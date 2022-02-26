package com.example.pokemontest.ui.fragments.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemontest.R
import com.example.pokemontest.data.model.Pokemon

class ListAdapter(val context: Context) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var pokemonList = emptyList<Pokemon>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photoView: ImageView = view.findViewById(R.id.photo_image_view)
        var nameView: TextView = view.findViewById(R.id.name_text_view)
        val pokemonItem: LinearLayout = view.findViewById(R.id.pokemon_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = pokemonList[position]
        holder.nameView.text = currentItem.name

        Glide.with(context)
            .load("https${currentItem.img.drop(4)}")
            .error(R.drawable.ic_broken_image)
            .into(holder.photoView)

        holder.pokemonItem.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = pokemonList.size

    fun setData(newList: List<Pokemon>) {
        pokemonList = newList
        notifyDataSetChanged()
    }

}