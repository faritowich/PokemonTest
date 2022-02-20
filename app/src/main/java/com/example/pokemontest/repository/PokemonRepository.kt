package com.example.pokemontest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemontest.data.PokemonDao
import com.example.pokemontest.model.Pokemon
import com.example.pokemontest.model.PokemonResponse
import com.example.pokemontest.network.RetrofitInstance
import retrofit2.Response
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val pokemonDao: PokemonDao) : Repository {

    override suspend fun getPokemonsFromDatabase(): List<Pokemon>{
        return withContext(IO){
            pokemonDao.getPokemonsFromDatabase()
        }
    }

    override suspend fun getPokemonResponseFromServer(): Response<PokemonResponse> {
        return RetrofitInstance.api.getPokemons()
    }

    override suspend fun savePokemonToDatabase(pokemon: Pokemon) {
        pokemonDao.addPokemonToDatabase(pokemon)
    }
}