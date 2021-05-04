package com.mccarty.comics.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mccarty.comics.R
import com.mccarty.comics.models.ItemXX

class SeriesAdapter(private val list: MutableList<ItemXX>):
    RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txtName)
        val resource: TextView = view.findViewById(R.id.textResource)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.series_item_layout,
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.resource.text = list[position].resourceURI
    }

    override fun getItemCount() = list.size

}