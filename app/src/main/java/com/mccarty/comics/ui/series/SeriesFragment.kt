package com.mccarty.comics.ui.series

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mccarty.comics.R
import com.mccarty.comics.adapters.SeriesAdapter
import com.mccarty.comics.models.ItemXX
import com.mccarty.comics.ui.main.MainViewModel
import com.mccarty.comics.utils.Utils
import kotlinx.android.synthetic.main.series_fragment.*

class SeriesFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: SeriesAdapter
    private lateinit var list: MutableList<ItemXX>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.series_fragment, container, false)
        recyclerView = root.findViewById(R.id.recyclerSeries)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return root
    }

    override fun onStart() {
        super.onStart()
        list = viewModel.navigateToView.value?.series?.items as MutableList<ItemXX>
        adapter = SeriesAdapter(list)
        adapter.notifyDataSetChanged()
        recyclerView.adapter = adapter

        setupUi()
    }

    private fun setupUi() {
        txtAttribution.text = Utils.getAttribute(requireActivity())
    }

}