package com.example.pokemontest.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.pokemontest.R
import com.example.pokemontest.data.PokemonDao
import com.example.pokemontest.data.PokemonDatabase
import com.example.pokemontest.model.Pokemon
import com.example.pokemontest.model.PokemonResponse
import com.example.pokemontest.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

enum class PokemonApiStatus { LOADING, ERROR, DONE }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    var readAllData: MutableLiveData<List<Pokemon>>? = null

    private val _pokemonList = MutableLiveData<Response<PokemonResponse>>()
    private val _status = MutableLiveData<PokemonApiStatus>()

    val pokemonList: MutableLiveData<Response<PokemonResponse>> = _pokemonList
    val status: LiveData<PokemonApiStatus> = _status


    init {
        val pokemonDao = PokemonDatabase.getDatabase(application).pokemonDao()
        repository = Repository(pokemonDao)
    }

    fun getPokemons() {
        viewModelScope.launch {
            PokemonApiStatus.LOADING
            try {
                val response: Response<PokemonResponse> = repository.getAllPokemons()
                _pokemonList.value = response
                savePokemonsToDatabase(response)
                readAllData?.value = repository.readAllData()
                _status.value = PokemonApiStatus.DONE
            } catch (e: Exception) {
                _status.value = PokemonApiStatus.ERROR
            }
        }
    }

    fun savePokemonsToDatabase(response: Response<PokemonResponse>){
        viewModelScope.launch(Dispatchers.IO) {
            for (pokemon in response.body()?.pokemons!!){
                repository.savePokemonToDatabase(pokemon)
            }
        }
    }
}