package com.example.pokemontest.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.pokemontest.data.PokemonDatabase
import com.example.pokemontest.model.Pokemon
import com.example.pokemontest.model.PokemonResponse
import com.example.pokemontest.repository.Repository
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository

    val pokemonList = MutableLiveData<List<Pokemon>>()

    init {
        val pokemonDao = PokemonDatabase.getDatabase(application).pokemonDao()
        repository = Repository(pokemonDao)
    }

    suspend fun getPokemons() {
        try {
            val response: Response<PokemonResponse> = repository.getAllPokemons()
            pokemonList.value = response.body()?.pokemons
            savePokemonsToDatabase(response)
        } catch (e: Exception) {
            pokemonList.value = repository.getPokemonsFromDatabase()
        }
    }

    suspend fun savePokemonsToDatabase(response: Response<PokemonResponse>) {
        if (response.isSuccessful) {
            for (pokemon in response.body()?.pokemons!!) {
                repository.savePokemonToDatabase(pokemon)
            }
        }
    }
}

