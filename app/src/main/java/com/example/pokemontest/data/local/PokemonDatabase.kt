package com.example.pokemontest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokemontest.data.model.Pokemon

@Database(entities = [Pokemon::class], exportSchema = false, version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun getPokemonDao(): PokemonDao
}