package com.example.enru_translator.ui.translator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enru_translator.data.api.IApiHelper
import com.example.enru_translator.data.local.IDBHelper
import com.example.enru_translator.data.local.entity.Word
import com.example.enru_translator.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TranslatorViewModel(
    private val apiHelper: IApiHelper,
    private val dbHelper: IDBHelper
) :
    ViewModel() {

    private var job: Job? = null
    private var word = MutableLiveData<Resource<Word>>()

    fun fetchWord(text: String) {
        job?.cancel()
        job = viewModelScope.launch {
            word.postValue(Resource.loading(null))
            try {
                val wordFromDb = dbHelper.getSearch(text)
                if (wordFromDb == null) {
                    val wordFromApi = apiHelper.getSearch(text)
                    val wordToInsertInDB: Word
                    wordToInsertInDB = if (text.trim()[0] > 'z') {
                        Word(
                            wordFromApi.def[0].translates[0].translateText,
                            wordFromApi.def[0].text
                        )
                    } else {
                        Word(
                            wordFromApi.def[0].text,
                            wordFromApi.def[0].translates[0].translateText
                        )
                    }
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


    fun getWord(): MutableLiveData<Resource<Word>> {
        return word
    }

}