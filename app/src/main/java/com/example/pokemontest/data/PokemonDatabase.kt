package com.example.pokemontest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokemontest.model.Pokemon

@Database(entities = [Pokemon::class], exportSchema = false, version = 1)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun getPokemonDao(): PokemonDao
}