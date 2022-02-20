package com.example.pokemontest.repository

import com.example.pokemontest.model.Pokemon
import com.example.pokemontest.model.PokemonResponse
import com.example.pokemontest.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

interface Repository {

    suspend fun getPokemonsFromDatabase(): List<Pokemon>

    suspend fun getPokemonResponseFromServer(): Response<PokemonResponse>

    suspend fun savePokemonToDatabase(pokemon: Pokemon)

}