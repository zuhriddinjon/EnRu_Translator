package com.example.enru_translator.data.model

import com.google.gson.annotations.SerializedName

data class WordPojo(val def: List<MainWord>)

data class MainWord(
    val text: String,
    val ts: String,
    @SerializedName("tr")
    val translates: List<Translates>
)

data class Translates(
    @SerializedName("text")
    val translateText: String,
    @SerializedName("syn")
    val synonym: List<Synonym>
)

data class Synonym(
    @SerializedName("text")
    val synonymText: String = "synonym is empty"
)
