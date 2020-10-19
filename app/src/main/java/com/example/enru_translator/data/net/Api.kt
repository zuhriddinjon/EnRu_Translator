package com.example.enru_translator.data.net

import androidx.lifecycle.LiveData
import com.example.enru_translator.common.KEY_API
import com.example.enru_translator.common.TRANSLATOR_URL
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET(TRANSLATOR_URL)
    suspend fun search(
        @Query("text")
        text: String,

        @Query("key")
        key: String = KEY_API,

        @Query("lang")
        lang: String = "en-ru"

    ): LiveData<WordPojo>
}