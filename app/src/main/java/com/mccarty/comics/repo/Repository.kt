package com.mccarty.comics.repo

import com.mccarty.comics.models.Result
import com.mccarty.comics.room.AppDatabase
import javax.inject.Inject

class Repository @Inject constructor(private val db: AppDatabase) {

    fun getAllCharacters() = db.characterDao().getAllCharacters()

    fun getAllCharactersDesc() = db.characterDao().getAllCharactersDesc()

    fun addCharactersToDb(characterList: Array<Result>) {
        db.characterDao().insertCharacters(characterList)
    }
}