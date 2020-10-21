package com.example.enru_translator.data.api

import com.example.enru_translator.common.KEY_API
import com.example.enru_translator.common.TRANSLATOR_URL
import com.example.enru_translator.data.model.WordPojo
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(TRANSLATOR_URL)
    suspend fun search(
        @Query("text")
        text: String,

        @Query("key")
        key: String = KEY_API,

        @Query("lang")
        lang: String = "en-ru"

    ): WordPojo
}