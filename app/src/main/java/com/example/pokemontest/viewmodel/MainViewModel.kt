package com.example.pokemontest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontest.model.Pokemon
import com.example.pokemontest.model.PokemonResponse
import com.example.pokemontest.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel : ViewModel() {

    val pokemonList = MutableLiveData<Response<PokemonResponse>>()
    private val repository: Repository

    init {
        repository = Repository()
    }

    fun getPokemons() {
        viewModelScope.launch {
            val response: Response<PokemonResponse> = repository.getAllPokemons()
            pokemonList.value = response
        }
    }

}