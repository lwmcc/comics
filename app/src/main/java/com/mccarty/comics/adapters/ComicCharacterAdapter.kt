package com.mccarty.comics.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mccarty.comics.R
import com.mccarty.comics.models.Result
import com.mccarty.comics.ui.main.MainViewModel



class ComicCharacterAdapter(
    private val viewModel: MainViewModel,
    private val list: MutableList<Result>
) :
    RecyclerView.Adapter<ComicCharacterAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imgThumb)
        val name: TextView = view.findViewById(R.id.txtName)
        val desc: TextView = view.findViewById(R.id.txtResource)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.result_item_layout,
            parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = list[position].thumbnail.path + "." + list[position].thumbnail.extension
        val appendHttps = "https" + url.subSequence(4, url.length)

        holder.name.text = list[position].name
        holder.desc.text = list[position].description
        Glide.with(holder.image.context)
            .load(appendHttps)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.ic_baseline_insert_photo_24)
            .centerCrop()
            .into(holder.image)

        holder.image.setOnClickListener {
            viewModel.navigateToDetails(list[position])
        }

        holder.desc.setOnClickListener {
            viewModel.navigateToDetails(list[position])
        }
    }

    override fun getItemCount() = list.size

}