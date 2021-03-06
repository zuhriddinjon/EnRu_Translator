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

    @Query("select * from word where (wordEn=:it or wordRu =:it) limit 1")
    fun search(it: String): Word?

    @Query("select * from word")
    fun allWord(): List<Word>

    @Query("delete from word")
    fun clearWord()

    @Query("delete from word where wordRu=:s")
    fun delete(s: String)

//    @Delete
//    fun delete(word: Word)
}