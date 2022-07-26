package com.example.pokemontest.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.pokemontest.data.local.PokemonDao
import com.example.pokemontest.data.local.PokemonDatabase
import com.example.pokemontest.data.model.Pokemon
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class PokemonDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: PokemonDatabase
    private lateinit var dao: PokemonDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PokemonDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getPokemonDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertPokemon() = runTest {
        val pokemon = Pokemon(1,
            "pikachu",
            "url",
            listOf("electric", "cute"),
            "0.1m",
            "2kg")

        dao.addPokemonToDatabase(pokemon)

        val allPokemons = dao.getPokemonsFromDatabase()
        assertThat(allPokemons).contains(pokemon)
    }


}