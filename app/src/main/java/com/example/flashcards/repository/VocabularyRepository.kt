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

    suspend fun insertDefaultWords() {
        if (vocabularyDao.getWordCount() == 0) { // Only insert if empty
            PredefinedVocabulary.words.forEach { vocabularyDao.insertWord(it) }
        }
    }

    suspend fun updateWord(word: Vocabulary) {
        vocabularyDao.updateWord(word)
    }

    suspend fun deleteWord(word: Vocabulary) {
        vocabularyDao.deleteWord(word)
    }

    suspend fun increaseScore(wordId: Int) {
        vocabularyDao.increaseScore(wordId)
    }

    suspend fun decreaseScore(wordId: Int) {
        vocabularyDao.decreaseScore(wordId)
    }

    suspend fun getAllWordsSortedByScoreDesc(): List<Vocabulary> = vocabularyDao.getAllWordsSortedByScoreDesc()

    suspend fun getAllWordsSortedByScoreAsc(): List<Vocabulary> = vocabularyDao.getAllWordsSortedByScoreAsc()

    fun getWordsByCategory(category: String): Flow<List<Vocabulary>> = vocabularyDao.getWordsByCategory(category)
}
