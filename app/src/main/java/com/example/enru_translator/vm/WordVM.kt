package com.example.enru_translator.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enru_translator.data.api.IApiHelper
import com.example.enru_translator.data.local.IDBHelper
import com.example.enru_translator.data.local.entity.Word
import com.example.enru_translator.utils.Resource
import kotlinx.coroutines.launch

class WordVM(private val apiHelper: IApiHelper, private val dbHelper: IDBHelper) :
    ViewModel() {

    private val word = MutableLiveData<Resource<Word>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            word.postValue(Resource.loading(null))
            try {
                val wordFromDb = dbHelper.getSearch("")
                if (wordFromDb == null) {
                    val wordFromApi = apiHelper.getSearch("")
                    val wordToInsertInDB = Word(
                        wordFromApi.def[0].text,
                        wordFromApi.def[0].translates[0].translateText
                    )
                    dbHelper.insert(wordToInsertInDB)

                    word.postValue(Resource.success(wordToInsertInDB))

                } else {
                    word.postValue(Resource.success(wordFromDb))
                }


            } catch (e: Exception) {
                word.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }

    fun getWord(): LiveData<Resource<Word>> {
        return word
    }

}