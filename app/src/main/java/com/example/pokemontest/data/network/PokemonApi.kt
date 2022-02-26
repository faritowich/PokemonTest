package com.example.pokemontest.data.network

import com.example.pokemontest.data.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET

interface PokemonApi {
    @GET("pokedex.json")
    suspend fun getPokemons(): Response<PokemonResponse>
}