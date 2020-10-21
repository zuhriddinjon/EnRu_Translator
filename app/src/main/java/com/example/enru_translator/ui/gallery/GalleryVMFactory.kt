package com.example.enru_translator.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.enru_translator.data.local.IDBHelper

class GalleryVMFactory(private val dbHelper: IDBHelper) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        modelClass.getConstructor(String::class.java).newInstance(arg)
        return GalleryViewModel(dbHelper) as T
    }

}