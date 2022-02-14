package com.example.pokemontest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemontest.data.PokemonDao
import com.example.pokemontest.model.Pokemon
import com.example.pokemontest.model.PokemonResponse
import com.example.pokemontest.network.RetrofitInstance
import retrofit2.Response

class Repository(private val pokemonDao: PokemonDao) {

    fun getPokemonsFromDatabase(): List<Pokemon> = pokemonDao.getPokemonsFromDatabase()

    suspend fun getAllPokemons(): Response<PokemonResponse> {
        return RetrofitInstance.api.getPokemons()
    }

    suspend fun savePokemonToDatabase(pokemon: Pokemon) {
        pokemonDao.addPokemonToDatabase(pokemon)
    }
}