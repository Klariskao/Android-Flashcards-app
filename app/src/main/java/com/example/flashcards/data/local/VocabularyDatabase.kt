package com.example.flashcards.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flashcards.data.model.Vocabulary

@Database(entities = [Vocabulary::class], version = 1, exportSchema = false)
abstract class VocabularyDatabase : RoomDatabase() {
    abstract fun vocabularyDao(): VocabularyDao

    companion object {
        @Volatile
        private var INSTANCE: VocabularyDatabase? = null

        fun getDatabase(context: Context): VocabularyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VocabularyDatabase::class.java,
                    "vocabulary_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}