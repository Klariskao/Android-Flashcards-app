package com.example.flashcards.ui.vocabulary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.model.Category
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

    private val _isDescending = MutableStateFlow(false)
    val isDescending: StateFlow<Boolean> = _isDescending

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
        _isDescending.value = !_isDescending.value
        getWordsSortedByScore(_isDescending.value)
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
        koreanWord: String,
        englishMeaning: String,
        category: Category,
    ) {
        val newWord =
            Vocabulary(
                koreanWord = koreanWord,
                englishMeaning = englishMeaning,
                category = category,
            )

        viewModelScope.launch {
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
