package com.example.enru_translator.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.enru_translator.data.api.IApiHelper
import com.example.enru_translator.data.local.IDBHelper

class HomeVMFactory(private val apiHelper: IApiHelper, private val dbHelper: IDBHelper) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        modelClass.getConstructor(String::class.java).newInstance(arg)
        return HomeViewModel(apiHelper, dbHelper) as T
    }

}