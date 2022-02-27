package com.example.pokemontest.domain

import com.example.pokemontest.data.local.PokemonDao
import com.example.pokemontest.data.model.Pokemon
import com.example.pokemontest.data.model.PokemonResponse
import com.example.pokemontest.data.network.PokemonApi
import retrofit2.Response
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val dao: PokemonDao, private val api: PokemonApi) :
    Repository {

    override suspend fun getPokemonsFromDatabase(): List<Pokemon> {
        return withContext(IO) {
            dao.getPokemonsFromDatabase()
        }
    }

    override suspend fun getPokemonResponseFromServer(): Response<PokemonResponse> {
        return api.getPokemons()
    }

    override suspend fun savePokemonToDatabase(pokemon: Pokemon) {
        dao.addPokemonToDatabase(pokemon)
    }
}