package com.example.enru_translator.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.enru_translator.data.api.IApiHelper
import com.example.enru_translator.data.local.IDBHelper
import com.example.enru_translator.data.local.entity.Word
import com.example.enru_translator.utils.Resource

class RepoImpl(
//    private val api: ApiService,
//    private val dao: WordDao
    private val apiHelper: IApiHelper,
    private val dbHelper: IDBHelper
) : IRepo {
    private val word = MutableLiveData<Resource<Word>>()

    override suspend fun search(searchedWord: String): LiveData<Resource<Word>> {
        word.postValue(Resource.loading(null))
        try {
            val wordFromDb = dbHelper.getSearch()
            if (wordFromDb == null) {
                val wordFromApi = apiHelper.getSearch()
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


//        var word = dao.search(searchedWord)
//        if (word == null) {
//            val wordPojo = api.search(searchedWord)
//            Log.d("search: ", "${api}")
//            if (wordPojo != null) {
//                if (wordPojo.def.isNotEmpty()) {
//                    val dbWord = Word(searchedWord, wordPojo.def[0].translates[0].translateText)
//                    dao.insert(dbWord)
//                    word = dbWord
//                } else {
//
//                }
//            } else {
//
//            }
//
//        } else {
//
//        }
        return liveData { word }
    }

//    companion object {
//        private var instance: RepoImpl? = null
//        fun instance(): RepoImpl {
//            var local = instance
//            if (local != null) {
//                val api = ApiProvider.instance()
//                local = RepoImpl(api)
//            }
//        }
//    }
}