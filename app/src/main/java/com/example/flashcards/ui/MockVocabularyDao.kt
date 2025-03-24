package com.example.flashcards.ui

import com.example.flashcards.data.local.VocabularyDao
import com.example.flashcards.data.model.Vocabulary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

class MockVocabularyDao : VocabularyDao {
    private val sampleData =
        MutableStateFlow(
            listOf(
                Vocabulary(id = 1, koreanWord = "사과", englishMeaning = "Apple", isFavorite = true),
                Vocabulary(
                    id = 2,
                    koreanWord = "바나나",
                    englishMeaning = "Banana",
                    isFavorite = false,
                ),
                Vocabulary(id = 3, koreanWord = "고양이", englishMeaning = "Cat", isFavorite = true),
            ),
        )

    override fun getAllWords(): Flow<List<Vocabulary>> = sampleData

    override fun getFavoriteWords(): Flow<List<Vocabulary>> = emptyFlow()

    override fun getWordsByCategory(category: String): Flow<List<Vocabulary>> = emptyFlow()

    override suspend fun increaseScore(wordId: Int) {}

    override suspend fun decreaseScore(wordId: Int) {}

    override suspend fun getAllWordsSortedByScoreDesc(): List<Vocabulary> = emptyList()

    override suspend fun getAllWordsSortedByScoreAsc(): List<Vocabulary> = emptyList()

    override suspend fun insertWord(word: Vocabulary): Long = 0L

    override suspend fun updateWord(word: Vocabulary): Int = 0

    override suspend fun deleteWord(word: Vocabulary): Int = 0
}
