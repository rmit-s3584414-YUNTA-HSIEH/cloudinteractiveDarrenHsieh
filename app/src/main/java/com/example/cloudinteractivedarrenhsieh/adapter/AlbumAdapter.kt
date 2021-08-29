package com.example.cloudinteractivedarrenhsieh.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudinteractivedarrenhsieh.R
import com.example.cloudinteractivedarrenhsieh.data.Album
import com.example.cloudinteractivedarrenhsieh.loader.ImageLoader


class AlbumAdapter(private val items: List<Album>,private val context: Context):
RecyclerView.Adapter<AlbumAdapter.ListViewHolder>(){

    class ListViewHolder(view: View): RecyclerView.ViewHolder(view){
        var image: ImageView = view.findViewById(R.id.imageView)
        var idText: TextView = view.findViewById(R.id.recyclerIdText)
        var titleText: TextView = view.findViewById(R.id.recyclerTitleText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_item,parent,false
        )

        val size = calculateViewSize(context)
        //configure height and width of the item in grid layout
        val layoutParams = GridLayout.LayoutParams(ViewGroup.LayoutParams(size,size))

        view.layoutParams =layoutParams
        return ListViewHolder(view)

    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        ImageLoader.showImage(items[position].thumbnailUrl,holder.image,context)
        holder.idText.text = items[position].id.toString()
        holder.titleText.text = items[position].title

        //click item and pass data to the next view
        holder.image.setOnClickListener {
            val bundle: Bundle = bundleOf(Pair("position", position))
            it.findNavController().navigate(R.id.action_gridFragment_to_itemFragment,bundle)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    //
    private fun calculateViewSize(context: Context): Int{
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels

        return (dpWidth/4)
        //four grids per row
    }

}