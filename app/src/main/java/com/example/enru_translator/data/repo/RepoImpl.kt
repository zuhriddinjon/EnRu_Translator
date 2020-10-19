package com.example.enru_translator.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.enru_translator.data.db.WordDao
import com.example.enru_translator.data.model.Word
import com.example.enru_translator.data.net.Api
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.singleOrNull

class RepoImpl(
    private val api: Api,
    private val dao: WordDao
) : IRepo {

    override suspend fun search(searchedWord: String): LiveData<Word> {
        var word = dao.search(searchedWord).singleOrNull()
        if (word == null) {
            val wordPojo = api.search(searchedWord)
            Log.d("search: ", "${api}")
            if (wordPojo != null) {
                if (wordPojo.def.isNotEmpty()) {
                    val dbWord = Word(searchedWord, wordPojo.def[0].translate[0].translateText)
                    dao.insert(dbWord)
                    word = dbWord
                } else {

                }
            } else {

            }

        } else {

        }
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