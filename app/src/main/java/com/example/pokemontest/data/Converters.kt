package com.example.pokemontest.data

import androidx.room.TypeConverter
import java.util.stream.Collectors


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