package com.example.pokemontest.di

import android.content.Context
import androidx.room.Room
import com.example.pokemontest.data.local.PokemonDao
import com.example.pokemontest.data.local.PokemonDatabase
import com.example.pokemontest.data.network.PokemonApi
import com.example.pokemontest.domain.PokemonRepository
import com.example.pokemontest.domain.Repository
import com.example.pokemontest.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/"
    private const val POKEMON_DATABASE_NAME = "pokemon_db"

    @Singleton
    @Provides
    fun providePokemonDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, PokemonDatabase::class.java, POKEMON_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDao(db: PokemonDatabase) = db.getPokemonDao()

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        okhttp3.OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): PokemonApi =
        retrofit.create(PokemonApi::class.java)

    @Singleton
    @Provides
    fun providesRepository(dao: PokemonDao, apiService: PokemonApi) : PokemonRepository{
        return PokemonRepository(dao, apiService)
    }
}

