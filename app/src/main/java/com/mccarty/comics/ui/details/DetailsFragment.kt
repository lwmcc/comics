package com.mccarty.comics.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mccarty.comics.R
import com.mccarty.comics.ui.main.MainViewModel
import kotlinx.android.synthetic.main.details_fragment.*

class DetailsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()

        setupUi()
    }

    private fun setupUi() {
        // TODO:
        val url = viewModel.getCharacterImageRemote()
        val https = viewModel.appendHttps(url)

        Glide.with(imgCharacter.context)
            .load(https)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.ic_baseline_insert_photo_24)
            .centerCrop()
            .into(imgCharacter)

        txtName.text = viewModel.getCharacterName()
        txtResource.text = viewModel.getCharacterDesc()
        txtSeries.text = viewModel.getCharacterSeriesNumber(requireContext().getString(R.string.number_of_series))
        txtSeries.setOnClickListener { view ->
            view.findNavController().navigate(R.id.seriesFragment)
        }
    }

}