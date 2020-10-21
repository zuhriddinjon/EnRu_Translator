package com.example.enru_translator.data.api

import com.example.enru_translator.data.model.WordPojo

class ApiHelperImpl(private val apiService: ApiService, private val text: String) :
    IApiHelper {
    override suspend fun getSearch(): WordPojo = apiService.search(text)
}