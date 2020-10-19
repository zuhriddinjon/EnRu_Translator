package com.example.enru_translator.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.enru_translator.data.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: Word)

    @Query("select * from word where wordEn=:it limit 1")
    fun search(it: String): Flow<Word>

    @Query("select * from word")
    fun allWord(): Flow<List<Word>>

    @Query("delete from word")
    fun clearWord()
}