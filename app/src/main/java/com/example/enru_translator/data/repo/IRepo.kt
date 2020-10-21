package com.example.enru_translator.data.repo

import androidx.lifecycle.LiveData
import com.example.enru_translator.data.local.entity.Word
import com.example.enru_translator.utils.Resource

interface IRepo {
    suspend fun search(searchedWord: String): LiveData<Resource<Word>>
}