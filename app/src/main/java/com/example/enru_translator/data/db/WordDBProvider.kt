package com.example.enru_translator.data.db

import android.content.Context
import androidx.room.Room

class WordDBProvider {
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var instance: WordDB? = null

        fun instance(context: Context): WordDB {
            var local = instance
            if (local != null) {
                return local
            }
            synchronized(this) {
                local = Room.databaseBuilder(
                    context.applicationContext,
                    WordDB::class.java,
                    "word_database"
                ).build()
                instance = local
                return instance!!
            }
        }
    }
}