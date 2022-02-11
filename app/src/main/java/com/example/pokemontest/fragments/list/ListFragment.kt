package com.example.pokemontest.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontest.R
import com.example.pokemontest.databinding.FragmentListBinding
import com.example.pokemontest.model.Pokemon
import com.example.pokemontest.network.PokemonApi
import com.example.pokemontest.repository.Repository
import com.example.pokemontest.viewmodel.ListViewModel

class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
            } else {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
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