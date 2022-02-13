package com.example.pokemontest.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pokemontest.data.Converters
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@TypeConverters(Converters::class)
@Entity(tableName = "pokemon_table")
data class Pokemon(
    @PrimaryKey
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