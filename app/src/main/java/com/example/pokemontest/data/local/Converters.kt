package com.example.pokemontest.data.local

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromType(type: List<String>): String {
        return type.joinToString(" ")
    }

    @TypeConverter
    fun toType(type: String): List<String> {
        return type.split(" ")
    }

}