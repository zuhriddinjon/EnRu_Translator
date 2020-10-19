package com.example.enru_translator.data.net

import com.example.enru_translator.common.BASE_URL
import com.example.enru_translator.data.net.retrofit.CoroutineCallAdapterFactory
import com.example.enru_translator.data.net.retrofit.RetrofitBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiProvider {

    companion object {
        private var instance: Api? = null
        fun instance(): Api {
            var local = instance
            if (local == null) {
                val retrofit = RetrofitBuilder.build(BASE_URL)

                local = retrofit.create(Api::class.java)
                instance = local
                return instance!!
            }
            return instance!!
        }
    }
}