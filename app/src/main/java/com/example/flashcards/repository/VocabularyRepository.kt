package com.example.flashcards.repository

import com.example.flashcards.data.local.VocabularyDao
import com.example.flashcards.data.model.Vocabulary
import kotlinx.coroutines.flow.Flow

class VocabularyRepository(
    private val vocabularyDao: VocabularyDao,
) {
    val allWords: Flow<List<Vocabulary>> = vocabularyDao.getAllWords()
    val favoriteWords: Flow<List<Vocabulary>> = vocabularyDao.getFavoriteWords()

    suspend fun insertWord(word: Vocabulary) {
        vocabularyDao.insertWord(word)
    }

    suspend fun updateWord(word: Vocabulary) {
        vocabularyDao.updateWord(word)
    }

    suspend fun deleteWord(word: Vocabulary) {
        vocabularyDao.deleteWord(word)
    }

    fun getWordsByCategory(category: String): Flow<List<Vocabulary>> = vocabularyDao.getWordsByCategory(category)
}
