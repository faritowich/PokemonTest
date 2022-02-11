package com.example.pokemontest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("img")
    val img: String,
    @SerializedName("type")
    val type: List<String>,
    @SerializedName("height")
    val height: String,
    @SerializedName("weight")
    val weight: String,
): Parcelable