package com.example.pokemontest.fragments.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pokemontest.R
import com.example.pokemontest.model.Pokemon

class ListAdapter(val context: Context) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var pokemonList = emptyList<Pokemon>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photoView: ImageView = view.findViewById(R.id.photo_image_view)
        var nameView: TextView = view.findViewById(R.id.name_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = pokemonList[position]
        holder.nameView.text = currentItem.name

//        Glide.with(context)
//            .load("http://pokeapi.co/media/sprites/pokemon/" + currentItem.url + ".png")
//            .centerCrop()
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .into(holder.photoView)
    }

    override fun getItemCount(): Int = pokemonList.size

    fun setData(newList: List<Pokemon>){
        pokemonList = newList
        notifyDataSetChanged()
    }

}