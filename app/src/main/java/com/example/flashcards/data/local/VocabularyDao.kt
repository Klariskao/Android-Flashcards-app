package com.example.flashcards.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flashcards.data.model.Vocabulary
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabularyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Vocabulary): Long // Returns the inserted row ID

    @Update
    suspend fun updateWord(word: Vocabulary): Int // Returns number of rows updated

    @Delete
    suspend fun deleteWord(word: Vocabulary): Int // Returns number of rows deleted

    @Query("SELECT * FROM vocabulary ORDER BY koreanWord ASC")
    fun getAllWords(): Flow<List<Vocabulary>>

    @Query("SELECT * FROM vocabulary WHERE isFavorite = 1")
    fun getFavoriteWords(): Flow<List<Vocabulary>>

    @Query("SELECT * FROM vocabulary WHERE category = :category")
    fun getWordsByCategory(category: String): Flow<List<Vocabulary>>
}
