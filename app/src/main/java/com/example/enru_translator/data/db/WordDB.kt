package com.example.enru_translator.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.enru_translator.data.model.Word

@Database(entities = [Word::class], version = 1)
abstract class WordDB : RoomDatabase() {

    abstract fun wordDao(): WordDao

}
