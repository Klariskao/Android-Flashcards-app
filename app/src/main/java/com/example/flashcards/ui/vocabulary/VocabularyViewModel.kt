package com.example.flashcards.ui.vocabulary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.model.Category
import com.example.flashcards.data.model.Vocabulary
import com.example.flashcards.repository.VocabularyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VocabularyViewModel(
    private val repository: VocabularyRepository,
) : ViewModel() {
    private val _vocabularyList = MutableStateFlow<List<Vocabulary>>(emptyList())
    val vocabularyList: StateFlow<List<Vocabulary>> = _vocabularyList

    private val _isDescending = MutableStateFlow(false)
    val isDescending: StateFlow<Boolean> = _isDescending

    private val _showOnlyFavorites = MutableStateFlow(false)
    val showOnlyFavorites: StateFlow<Boolean> = _showOnlyFavorites

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory

    // Combine repository data with sorting & filtering logic
    val words: StateFlow<List<Vocabulary>> =
        combine(
            vocabularyList,
            isDescending,
            showOnlyFavorites,
            selectedCategory,
        ) { list, isDescending, showOnlyFavorites, selectedCategory ->
            var filteredList = list

            // Apply Category Filtering
            if (selectedCategory != null) {
                filteredList = filteredList.filter { it.category == selectedCategory }
            }

            // Apply Favorite Filtering
            if (showOnlyFavorites) {
                filteredList = filteredList.filter { it.isFavorite }
            }

            // Apply Sorting
            if (isDescending) {
                filteredList.sortedByDescending { it.score }
            } else {
                filteredList.sortedBy { it.score }
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

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
    }

    fun toggleFavoriteFilter() {
        _showOnlyFavorites.value = !_showOnlyFavorites.value
    }

    fun selectCategory(category: Category?) {
        _selectedCategory.value = category
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
        loadVocabulary()
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
