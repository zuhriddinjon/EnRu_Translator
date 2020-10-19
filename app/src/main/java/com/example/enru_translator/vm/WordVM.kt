package com.example.enru_translator.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.enru_translator.data.db.WordDBProvider
import com.example.enru_translator.data.model.Word
import com.example.enru_translator.data.net.ApiProvider
import com.example.enru_translator.data.repo.RepoImpl

class WordVM : ViewModel() {
    private val api by lazy(LazyThreadSafetyMode.NONE) { ApiProvider.instance() }

    suspend fun searchWord(ctx: Context, it: String): Word {
        val dao = WordDBProvider.instance(ctx)
        val repo = RepoImpl(api, dao.wordDao())
        return repo.search(it).value!!
    }
}