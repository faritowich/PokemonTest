package com.example.pokemontest.di

import android.content.Context
import androidx.room.Room
import com.example.pokemontest.data.PokemonDatabase
import com.example.pokemontest.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, PokemonDatabase::class.java, Constants.POKEMON_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDao(db: PokemonDatabase) = db.getPokemonDao()
}