package com.example.enru_translator.data.local

import com.example.enru_translator.data.local.entity.Word

class DBHelperImpl(private val wordDB: WordDB) : IDBHelper {

    override suspend fun getSearch(text: String): Word? {
        return wordDB.wordDao().search(text)
    }

    override suspend fun insert(word: Word) = wordDB.wordDao().insert(word)

    override suspend fun getAll() = wordDB.wordDao().allWord()

    override suspend fun delete(word: Word) = wordDB.wordDao().delete(word.wordRu)
}