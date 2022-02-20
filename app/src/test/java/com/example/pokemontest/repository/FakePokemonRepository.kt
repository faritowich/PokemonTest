package com.example.pokemontest.repository

import androidx.lifecycle.MutableLiveData
import com.example.pokemontest.model.Pokemon
import com.example.pokemontest.model.PokemonResponse
import retrofit2.Response

class FakePokemonRepository : Repository {

    val pokemonList = mutableListOf<Pokemon>()

    val observablePokemonList = MutableLiveData<List<Pokemon>>(pokemonList)

    private var shouldReturnNetworkError = false

    private fun refreshLiveData() {
        observablePokemonList.postValue(pokemonList)
    }

    override suspend fun getPokemonsFromDatabase(): List<Pokemon> {
        return pokemonList
    }

    override suspend fun getPokemonResponseFromServer(): Response<PokemonResponse> {
        TODO()
    }

    override suspend fun savePokemonToDatabase(pokemon: Pokemon) {
        pokemonList.add(pokemon)
        refreshLiveData()
    }

}