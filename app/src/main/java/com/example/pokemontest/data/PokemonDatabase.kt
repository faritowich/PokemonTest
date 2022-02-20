package com.example.pokemontest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokemontest.model.Pokemon

@Database(entities = arrayOf(Pokemon::class), exportSchema = false, version = 1)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun getPokemonDao(): PokemonDao

//    companion object {
//        //Создаем синглтон UserDatabase, видимый для всех потоков
//        @Volatile
//        private var INSTANCE: PokemonDatabase? = null
//
//        fun getDatabase(context: Context): PokemonDatabase {
//            val tempInstance = INSTANCE
//
//            // If our instance already exists we return this instance
//            if (tempInstance != null) {
//                return tempInstance
//            }
//            // Else if it doesn't exist (if tempInstance is null) we create it:
//            synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context,
//                    PokemonDatabase::class.java,
//                    "user_database"
//                ).build()
//                INSTANCE = instance
//                return instance
//            }
//        }
//    }
}