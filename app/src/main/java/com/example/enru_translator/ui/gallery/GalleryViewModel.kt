package com.example.enru_translator.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enru_translator.data.local.IDBHelper
import com.example.enru_translator.data.local.entity.Word
import com.example.enru_translator.utils.Resource
import kotlinx.coroutines.launch

class GalleryViewModel(private val dbHelper: IDBHelper) : ViewModel() {

    private val list = MutableLiveData<Resource<List<Word>>>()

    init {
        fetchWords()
    }

    private fun fetchWords() {
        viewModelScope.launch {
            list.postValue(Resource.loading(null))
            try {
                val list1 = dbHelper.getAll()
                list.postValue(Resource.success(list1))
            } catch (e: Exception) {
                list.postValue(Resource.error("Gallery is empty", null))

            }
        }
    }

    fun getList(): MutableLiveData<Resource<List<Word>>> {
        return list
    }

}