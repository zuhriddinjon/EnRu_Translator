package com.example.enru_translator.data.local

import android.util.Log
import com.example.enru_translator.data.local.entity.Word

class DBHelperImpl(private val wordDB: WordDB) : IDBHelper {

    override suspend fun getSearch(text: String): Word? {
        Log.d("getSearch: ", "$text")
        return wordDB.wordDao().search(text)
    }

    override suspend fun insert(word: Word) = wordDB.wordDao().insert(word)

    override suspend fun getAll() = wordDB.wordDao().allWord()
}