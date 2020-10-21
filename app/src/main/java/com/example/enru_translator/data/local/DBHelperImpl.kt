package com.example.enru_translator.data.local

import android.util.Log
import com.example.enru_translator.data.local.entity.Word

class DatabaseHelperImpl(private val wordDB: WordDB, private var text: String? =null) : IDBHelper {

    override suspend fun getSearch(): Word? {
        Log.d("getSearch: ", "${wordDB.wordDao().search(text!!)}")
        return wordDB.wordDao().search(text!!)
    }

    override suspend fun insert(word: Word) = wordDB.wordDao().insert(word)

    override suspend fun getAll() = wordDB.wordDao().allWord()
}