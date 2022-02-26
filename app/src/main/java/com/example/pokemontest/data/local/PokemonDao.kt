package com.example.pokemontest.data.local

import androidx.room.*
import com.example.pokemontest.data.model.Pokemon

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon_table ORDER BY id ASC")
    fun getPokemonsFromDatabase(): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemonToDatabase(pokemon: Pokemon)
}