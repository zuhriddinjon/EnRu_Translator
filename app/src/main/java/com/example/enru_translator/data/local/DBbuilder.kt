package com.example.enru_translator.data.local

import android.content.Context
import androidx.room.Room

object DBbuilder {

    private var instance: WordDB? = null

    fun getInstance(context: Context): WordDB {
        if (instance == null) {
            synchronized(WordDB::class) {
                instance = buildRoomDB(context)
            }
        }
        return instance!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            WordDB::class.java,
            "word_database.db"
        )
            .allowMainThreadQueries()
            .build()

}