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
import okhttp3.Dispatcher

class Repository(private val pokemonDao: PokemonDao) {

    suspend fun getPokemonsFromDatabase(): List<Pokemon>{
        return withContext(IO){
            pokemonDao.getPokemonsFromDatabase()
        }
    }
    //    fun getPokemonsFromDatabase(): List<Pokemon> = pokemonDao.getPokemonsFromDatabase()

    suspend fun getAllPokemons(): Response<PokemonResponse> {
        return RetrofitInstance.api.getPokemons()
    }

    suspend fun savePokemonToDatabase(pokemon: Pokemon) {
        pokemonDao.addPokemonToDatabase(pokemon)
    }
}