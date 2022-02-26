package com.example.pokemontest.domain

import com.example.pokemontest.data.model.Pokemon
import com.example.pokemontest.data.model.PokemonResponse
import retrofit2.Response

interface Repository {

    suspend fun getPokemonsFromDatabase(): List<Pokemon>

    suspend fun getPokemonResponseFromServer(): Response<PokemonResponse>

    suspend fun savePokemonToDatabase(pokemon: Pokemon)

}