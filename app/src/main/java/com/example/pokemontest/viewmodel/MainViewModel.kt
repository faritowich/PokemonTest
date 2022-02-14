package com.example.pokemontest.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.pokemontest.data.PokemonDatabase
import com.example.pokemontest.model.Pokemon
import com.example.pokemontest.model.PokemonResponse
import com.example.pokemontest.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import android.net.NetworkInfo

import androidx.core.content.ContextCompat.getSystemService

import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat


enum class PokemonApiStatus { ERROR, DONE }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository

    private val _offlinePokemonList = MutableLiveData<List<Pokemon>>()
    private val _onlinePokemonList = MutableLiveData<Response<PokemonResponse>>()
    private val _status = MutableLiveData<PokemonApiStatus>()

    val offlinePokemonList: MutableLiveData<List<Pokemon>> = _offlinePokemonList
    val onlinePokemonList: MutableLiveData<Response<PokemonResponse>> = _onlinePokemonList
    val status: LiveData<PokemonApiStatus> = _status


    init {
        val pokemonDao = PokemonDatabase.getDatabase(application).pokemonDao()
        repository = Repository(pokemonDao)
    }

    fun getPokemons() {
        viewModelScope.launch {
            try {
                val response: Response<PokemonResponse> = repository.getAllPokemons()
                _onlinePokemonList.value = response
                savePokemonsToDatabase(response)
                readPokemonDatabase()
                _status.value = PokemonApiStatus.DONE
            } catch (e: Exception) {
                _status.value = PokemonApiStatus.ERROR
                _offlinePokemonList.value = _onlinePokemonList.value?.body()?.pokemons
            }
        }
    }

    fun savePokemonsToDatabase(response: Response<PokemonResponse>) {
        viewModelScope.launch(Dispatchers.IO) {
            if (response.isSuccessful) {
                for (pokemon in response.body()?.pokemons!!) {
                    repository.savePokemonToDatabase(pokemon)
                }
            }
        }
    }

    fun readPokemonDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            _offlinePokemonList.postValue(repository.getPokemonsFromDatabase())
        }
    }
}