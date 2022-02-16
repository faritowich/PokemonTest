package com.example.pokemontest.network

import com.example.pokemontest.model.Pokemon
import com.example.pokemontest.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokedex.json")
    suspend fun getPokemons(): Response<PokemonResponse>
}