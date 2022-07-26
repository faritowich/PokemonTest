package com.example.pokemontest.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.pokemontest.data.model.Pokemon
import com.example.pokemontest.data.model.PokemonResponse
import com.example.pokemontest.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val pokemonRepository: Repository) :
    ViewModel() {

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    fun getPokemons() {
        viewModelScope.launch {
            try {
                val response: Response<PokemonResponse> = pokemonRepository.getPokemonResponseFromServer()
                _pokemonList.value = response.body()?.pokemons
                savePokemonsToDatabase(response)
            } catch (e: Exception) {
                _pokemonList.value = pokemonRepository.getPokemonsFromDatabase()
            }
        }
    }

    private suspend fun savePokemonsToDatabase(response: Response<PokemonResponse>) {
        if (response.isSuccessful) {
            for (pokemon in response.body()?.pokemons!!) {
                pokemonRepository.savePokemonToDatabase(pokemon)
            }
        }
    }
}