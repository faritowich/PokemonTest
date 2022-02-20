package com.example.pokemontest.ui.viewmodels

import androidx.lifecycle.*
import com.example.pokemontest.model.Pokemon
import com.example.pokemontest.model.PokemonResponse
import com.example.pokemontest.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val pokemonRepository: PokemonRepository) : ViewModel() {


    val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: MutableLiveData<List<Pokemon>> = _pokemonList

    suspend fun getPokemons() {
        try {
            val response: Response<PokemonResponse> = pokemonRepository.getPokemonResponseFromServer()
            _pokemonList.value = response.body()?.pokemons
            savePokemonsToDatabase(response)
        } catch (e: Exception) {
            _pokemonList.value = pokemonRepository.getPokemonsFromDatabase()
        }
    }

    suspend fun savePokemonsToDatabase(response: Response<PokemonResponse>) {
        if (response.isSuccessful) {
            for (pokemon in response.body()?.pokemons!!) {
                pokemonRepository.savePokemonToDatabase(pokemon)
            }
        }
    }
}

