package com.example.enru_translator.data.local

import com.example.enru_translator.data.local.entity.Word

interface IDBHelper {

    suspend fun getSearch(text: String): Word?

    suspend fun insert(word: Word)

    suspend fun getAll(): List<Word>

    suspend fun delete(word: Word)

}
