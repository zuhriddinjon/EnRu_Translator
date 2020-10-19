package com.example.enru_translator.data.model

import androidx.room.TypeConverter
import com.example.enru_translator.data.net.Synonym
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    val gson = Gson()

    @TypeConverter
    fun stringToList(data: String): List<Synonym> {
        if (data == null) return emptyList()
        val listType = object : TypeToken<List<Synonym>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(synonyms: List<Synonym>): String {
        return gson.toJson(synonyms)
    }
}