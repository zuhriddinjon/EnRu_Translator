package com.example.enru_translator.ui.translator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.enru_translator.data.api.IApiHelper
import com.example.enru_translator.data.local.IDBHelper

class TranslatorVMFactory(
    private val apiHelper: IApiHelper,
    private val dbHelper: IDBHelper
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return TranslatorViewModel(apiHelper, dbHelper) as T
    }

}