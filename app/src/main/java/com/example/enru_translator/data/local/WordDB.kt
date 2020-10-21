package com.example.enru_translator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.enru_translator.data.local.dao.WordDao
import com.example.enru_translator.data.local.entity.Word

@Database(entities = [Word::class], version = 1)
abstract class WordDB : RoomDatabase() {

    abstract fun wordDao(): WordDao

}
