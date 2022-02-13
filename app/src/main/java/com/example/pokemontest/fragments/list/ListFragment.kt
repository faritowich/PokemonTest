package com.example.pokemontest.fragments.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.*
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontest.R
import com.example.pokemontest.databinding.FragmentListBinding
import com.example.pokemontest.network.RetrofitInstance
import com.example.pokemontest.viewmodel.MainViewModel
import com.example.pokemontest.viewmodel.PokemonApiStatus

import androidx.room.Room
import com.example.pokemontest.data.PokemonDatabase


class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Room.databaseBuilder(requireContext(), PokemonDatabase::class.java, "pokemon_table").build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        setRecyclerView()

        viewModel.getPokemons()

        viewModel.pokemonList.observe(this, Observer { response ->
            if (response.isSuccessful) {
                response.body()?.let { adapter.setData(it.pokemons) }
                Toast.makeText(requireContext(), "Downloaded", Toast.LENGTH_SHORT).show()
            } else {
//                viewModel.offlinePokemonList.value?.let { adapter.setData(it.pokemons) }
                Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.status.observe(this, Observer { loadingStatus ->
            when (loadingStatus){
                PokemonApiStatus.LOADING -> {
                    Toast.makeText(requireContext(), "LOADING", Toast.LENGTH_SHORT).show()
                }
                PokemonApiStatus.ERROR -> {
                    viewModel.pokemonList.value?.body()?.let { adapter.setData(listOf()) }
//                    Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
                }
                PokemonApiStatus.DONE -> {
                    Toast.makeText(requireContext(), "DONE", Toast.LENGTH_SHORT).show()
                }
            }
        })

        return binding.root
    }

    fun setRecyclerView(){
        adapter = ListAdapter(requireContext())
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = adapter
    }
}



