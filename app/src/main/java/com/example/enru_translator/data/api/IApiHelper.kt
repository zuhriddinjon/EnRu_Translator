package com.example.enru_translator.data.api

import com.example.enru_translator.data.model.WordPojo

interface IApiHelper {
    suspend fun getSearch(): WordPojo
}