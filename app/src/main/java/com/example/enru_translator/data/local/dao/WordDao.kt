package com.example.enru_translator.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.enru_translator.data.local.entity.Word

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: Word)

    @Query("select * from word where wordEn=:it limit 1")
    fun search(it: String): Word?

    @Query("select * from word")
    fun allWord(): List<Word>

    @Query("delete from word")
    fun clearWord()
}