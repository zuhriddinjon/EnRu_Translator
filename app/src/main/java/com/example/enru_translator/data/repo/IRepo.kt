package com.example.enru_translator.data.repo

import androidx.lifecycle.LiveData
import com.example.enru_translator.data.model.Word

interface IRepo {
    suspend fun search(searchedWord: String): LiveData<Word>
}