package com.example.flashcards.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vocabulary")
data class Vocabulary(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val koreanWord: String,
    val englishMeaning: String,
    val category: String = "General",
    val isFavorite: Boolean = false,
    val score: Int = 0
)