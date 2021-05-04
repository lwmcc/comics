package com.mccarty.comics.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.*
import com.mccarty.comics.api.ComicCharacterService
import com.mccarty.comics.models.Result
import com.mccarty.comics.repo.Repository
import com.mccarty.comics.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    // TODO: move retrofit
    private val retrofit: Retrofit): ViewModel() {

    val allCharacters: MutableLiveData<Array<Result>> by lazy {
        MutableLiveData<Array<Result>>()
    }

    val charactersInsertedToDb: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val navigateToView: MutableLiveData<Result> by lazy {
        MutableLiveData<Result>()
    }

    val getAttribution: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    var hasAscendingOrder = true

    fun getAllComicCharactersLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            val characters: Array<Result> = repository.getAllCharacters()
            allCharacters.postValue(characters)
        }
    }

    fun getAllComicCharactersLocalDesc() {
        viewModelScope.launch(Dispatchers.IO) {
            val characters: Array<Result> = repository.getAllCharactersDesc()
            allCharacters.postValue(characters)
        }
    }

    fun getAllComicCharactersRemote() {
        val retro = retrofit.create(ComicCharacterService::class.java)
        val ts = System.currentTimeMillis().toString()
        val apikey = "a6d8024a31ed0aa9b99605b110d1d0bf"
        val hash = Utils.hashString(ts, "509a434bbc15abc53e38e6e813cb441edd97e9c1", "a6d8024a31ed0aa9b99605b110d1d0bf")

        viewModelScope.launch(Dispatchers.IO) {
            val response = retro.searchAllCharacters(ts, apikey, hash, 100)
            setAttributionText(response)

            try {
                when(responseIsOk(response)) {
                    true -> {
                        val data = response.getAsJsonObject(DATA)
                        val results = data.getAsJsonArray(JSON_OBJECT)
                        val characterList: Array<Result> = Gson().fromJson(results , Array<Result>::class.java)
                        repository.addCharactersToDb(characterList)
                    }
                    false -> {
                        // TODO: Something went wrong log this, or try again
                    }
                }
            } catch(error: JsonParseException) {
                // TODO: an error occured log this
            } finally {
                charactersInsertedToDb.postValue(true)
            }
        }
    }

    fun navigateToDetails(result: Result) {
        navigateToView.value = result
    }

    fun getCharacterImageRemote(): String? = Utils.concatPathParts(navigateToView.value?.thumbnail?.path, navigateToView.value?.thumbnail?.extension)

    fun appendHttps(url: String?): String? = Utils.appendHttps(url)

    fun getCharacterName(): String? = navigateToView.value?.name

    fun getCharacterDesc(): String? = navigateToView.value?.description

    fun getCharacterSeriesNumber(num: String): String? = num + navigateToView.value?.series?.items?.size

    private fun responseIsOk(response: JsonObject): Boolean {
        val code = response.getAsJsonPrimitive(CODE)
        return code.toString() == OK_CODE
    }

    private fun setAttributionText(response: JsonObject) {
        getAttribution.postValue(response.getAsJsonPrimitive(ATTRIBUTION).toString())
    }

    fun searchFilterList(search: String, list: List<Result>): List<Result> = list.filter { it.name == search}

    companion object {
        const val CODE = "code"
        const val ATTRIBUTION = "attributionText"
        const val DATA = "data"
        const val OK_CODE = "200"
        const val JSON_OBJECT = "results"
    }

}