package com.example.enru_translator.data.api

import com.example.enru_translator.data.model.WordPojo

class ApiHelperImpl(private val apiService: ApiService) :
    IApiHelper {
    override suspend fun getSearch(text: String): WordPojo {
        var lang = "en-ru"
        if (text.trim()[0] > 'z') lang = "ru-en"
        return apiService.search(text, lang = lang)
    }
}