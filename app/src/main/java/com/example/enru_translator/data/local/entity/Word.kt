package com.example.enru_translator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity
@TypeConverters(Converters::class)
class Word(
    val wordEn: String,
    val translates: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)