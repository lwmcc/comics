package com.mccarty.comics.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mccarty.comics.R
import com.mccarty.comics.adapters.ComicCharacterAdapter
import com.mccarty.comics.models.Result
import com.mccarty.comics.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.txtAttribution
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var adapter: ComicCharacterAdapter
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var resultsList: MutableList<Result>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultsList = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val root = inflater.inflate(R.layout.main_fragment, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerCharacter)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ComicCharacterAdapter(viewModel, resultsList)

        recyclerView.adapter = adapter

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showHideProgress(true)
        viewModel.hasAscendingOrder = true

        viewModel.getAllComicCharactersLocal()

        viewModel.allCharacters.observe(requireActivity(), Observer { characters ->

            characters?.let {
                if(it.isNotEmpty()) {
                    resultsList.addAll(it)
                    adapter.notifyDataSetChanged()
                    showHideProgress(false)
                } else {
                    showHideProgress(true)
                    getRemoteListOfCharacters()
                }
            } ?: run {
                showHideProgress(true)
                getRemoteListOfCharacters()
            }
        })

        viewModel.charactersInsertedToDb.observe(requireActivity(), Observer {updated ->
            if(updated) {
                viewModel.getAllComicCharactersLocal()
            }
        })

        viewModel.getAttribution.observe(requireActivity(), Observer {
            saveAttributionText(it)
            txtAttribution.text = it
        })

        setListeners()
        setupUi()

    }

    private fun setListeners() {
        btnSearch.setOnClickListener {
            showHideProgress(true)
            val filteredList = viewModel.searchFilterList(editCharactor.text.toString(), resultsList)
            resultsList.clear()
            resultsList.addAll(filteredList)
            adapter.notifyDataSetChanged()
            showHideProgress(false)
            if(filteredList.isEmpty()) {
                Toast.makeText(requireContext(), requireContext().getString(R.string.no_results), Toast.LENGTH_LONG).show()
            }
        }

        btnSort.setOnClickListener {
            showHideProgress(true)
            when(viewModel.hasAscendingOrder) {
                true -> {
                    viewModel.hasAscendingOrder = false
                    viewModel.getAllComicCharactersLocalDesc()
                }
                false -> {
                    viewModel.hasAscendingOrder = true
                    viewModel.getAllComicCharactersLocal()
                }
            }

            resultsList.clear()
        }

        btnResetList.setOnClickListener {
            showHideProgress(true)
            if(hasInternet()) {
                viewModel.getAllComicCharactersRemote()
            }
            resultsList.clear()
            adapter.notifyDataSetChanged()
            editCharactor.text.clear()
        }

    }

    private fun setupUi() {
        txtAttribution.text = Utils.getAttribute(requireActivity())
    }

    private fun showHideProgress(show: Boolean) {
        progressBar.isVisible = show
    }

    private fun hasInternet() = Utils.networkIsAvailable(requireContext())

    private fun getRemoteListOfCharacters() {
        when(Utils.networkIsAvailable(requireContext())) {
            true -> {
                viewModel.getAllComicCharactersRemote()
            }
            false -> {
                Toast.makeText(requireContext(), requireContext().getString(R.string.no_internet), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun saveAttributionText(attribution: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(getString(R.string.attribution_text), attribution)
            apply()
        }
    }

}