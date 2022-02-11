package com.example.pokemontest.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)