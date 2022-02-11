package com.example.pokemontest.repository

import com.example.pokemontest.model.Pokemon
import com.example.pokemontest.model.PokemonResponse
import com.example.pokemontest.network.RetrofitInstance
import retrofit2.Response

class Repository {

    suspend fun getAllPokemons(): Response<PokemonResponse>{
        return RetrofitInstance.api.getPokemons()
    }

}