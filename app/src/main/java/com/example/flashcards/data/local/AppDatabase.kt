package com.example.flashcards.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flashcards.data.model.Vocabulary

@Database(entities = [Vocabulary::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vocabularyDao(): VocabularyDao
}