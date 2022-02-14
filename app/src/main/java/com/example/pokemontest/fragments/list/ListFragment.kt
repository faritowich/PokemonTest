package com.example.pokemontest.fragments.list

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontest.databinding.FragmentListBinding
import com.example.pokemontest.viewmodel.MainViewModel
import com.example.pokemontest.viewmodel.PokemonApiStatus

import androidx.room.Room
import com.example.pokemontest.data.PokemonDatabase
import kotlinx.coroutines.launch
import android.net.NetworkInfo
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.getSystemService


class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        lifecycleScope.launch {
            viewModel.readPokemonDatabase()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        setRecyclerView()

        viewModel.getPokemons()

        viewModel.onlinePokemonList.observe(this, Observer { response ->
            if (response.isSuccessful) {
                response.body()?.let { adapter.setData(it.pokemons) }
                Toast.makeText(requireContext(), "Pokemons downloaded", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.offlinePokemonList.observe(this, Observer {

            lifecycleScope.launch {
                if (viewModel.status.value == PokemonApiStatus.ERROR) {
                    try {
                        viewModel.readPokemonDatabase()
                        adapter.setData(it)
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Offline mode", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })

        return binding.root
    }

    fun setRecyclerView() {
        adapter = ListAdapter(requireContext())
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = adapter
    }
}



