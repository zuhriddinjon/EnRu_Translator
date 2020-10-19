package com.example.enru_translator.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.enru_translator.data.net.Synonym


@Entity
@TypeConverters(Converters::class)
class Word(
    val wordEn: String,
    val wordRu: String,
    val synonyms: List<Synonym> = emptyList(),
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
