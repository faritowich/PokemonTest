package com.example.pokemontest.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.pokemontest.data.PokemonDatabase
import com.example.pokemontest.model.Pokemon
import com.example.pokemontest.model.PokemonResponse
import com.example.pokemontest.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

enum class PokemonApiStatus { ERROR, DONE }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository

    val pokemonList = MutableLiveData<List<Pokemon>>()

    private val _offlinePokemonList = MutableLiveData<List<Pokemon>>()
    private val _onlinePokemonList =
        MutableLiveData<Response<PokemonResponse>>()
    private val _status = MutableLiveData<PokemonApiStatus>()

    val offlinePokemonList: MutableLiveData<List<Pokemon>> = _offlinePokemonList
    val onlinePokemonList: MutableLiveData<Response<PokemonResponse>> =
        _onlinePokemonList
    val status: LiveData<PokemonApiStatus> = _status

    init {
        val pokemonDao = PokemonDatabase.getDatabase(application).pokemonDao()
        repository = Repository(pokemonDao)
    }

    suspend fun getPokemons() {
        try {
            val response: Response<PokemonResponse> = repository.getAllPokemons()
            Log.d("CUSTOMTAG", "ONLINE VIEWMODEL RESPONSE ${repository.getAllPokemons().body()?.pokemons}")
            pokemonList.value = response.body()?.pokemons
            Log.d("CUSTOMTAG", "ONLINE VIEWMODEL POKEMONLIST ${pokemonList.value}")
            savePokemonsToDatabase(response)
            _status.value = PokemonApiStatus.DONE
            Log.d("CUSTOMTAG", "ONLINE VIEWMODEL STATUS ${_status.value.toString()}")
        } catch (e: Exception) {
            viewModelScope.launch(Dispatchers.IO) {
                pokemonList.postValue(repository.getPokemonsFromDatabase())
                _status.postValue(PokemonApiStatus.ERROR)
                Log.d("CUSTOMTAG", "OFFLINE VIEWMODEL POKEMONLIST ${pokemonList.value.toString()}")
                Log.d("CUSTOMTAG", "OFFLINE VIEWMODEL STATUS ${_status.value.toString()}")
            }
        }
    }

    suspend fun savePokemonsToDatabase(response: Response<PokemonResponse>) {
        if (response.isSuccessful) {
            for (pokemon in response.body()?.pokemons!!) {
                repository.savePokemonToDatabase(pokemon)
            }
        }
    }
}