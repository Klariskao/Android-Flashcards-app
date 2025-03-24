package com.example.flashcards.ui.vocabulary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.model.Vocabulary
import com.example.flashcards.repository.VocabularyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class VocabularyViewModel(
    private val repository: VocabularyRepository,
) : ViewModel() {
    private val _vocabularyList = MutableStateFlow<List<Vocabulary>>(emptyList())
    val vocabularyList: StateFlow<List<Vocabulary>> = _vocabularyList

    private var isDescending = true

    init {
        loadVocabulary()
    }

    private fun loadVocabulary() {
        viewModelScope.launch {
            repository.allWords.collectLatest { words ->
                _vocabularyList.value = words
            }
        }
    }

    fun toggleSortOrder() {
        isDescending = !isDescending
        getWordsSortedByScore(isDescending)
    }

    fun toggleFavorite(word: Vocabulary) {
        viewModelScope.launch {
            val updatedWord = word.copy(isFavorite = !word.isFavorite)
            repository.updateWord(updatedWord)
        }
    }

    fun deleteWord(word: Vocabulary) {
        viewModelScope.launch {
            repository.deleteWord(word)
        }
    }

    fun addWord(
        korean: String,
        english: String,
    ) {
        viewModelScope.launch {
            val newWord =
                Vocabulary(
                    id = 0, // Room will auto-generate ID
                    koreanWord = korean,
                    englishMeaning = english,
                    isFavorite = false,
                )
            repository.insertWord(newWord)
        }
    }

    fun updateWord(updatedWord: Vocabulary) {
        viewModelScope.launch {
            repository.updateWord(updatedWord)
        }
    }

    private fun getWordsSortedByScore(isDecs: Boolean) {
        viewModelScope.launch {
            if (isDecs) {
                _vocabularyList.value = repository.getAllWordsSortedByScoreDesc()
            } else {
                _vocabularyList.value = repository.getAllWordsSortedByScoreAsc()
            }
        }
    }
}
