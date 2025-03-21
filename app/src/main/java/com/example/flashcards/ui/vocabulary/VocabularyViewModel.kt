package com.example.flashcards.ui.vocabulary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.model.Vocabulary
import com.example.flashcards.repository.VocabularyRepository
import kotlinx.coroutines.launch

class VocabularyViewModel(private val repository: VocabularyRepository) : ViewModel() {
    val allWords = repository.allWords
    val favoriteWords = repository.favoriteWords

    fun addWord(word: Vocabulary) {
        viewModelScope.launch {
            repository.insertWord(word)
        }
    }

    fun updateWord(word: Vocabulary) {
        viewModelScope.launch {
            repository.updateWord(word)
        }
    }

    fun deleteWord(word: Vocabulary) {
        viewModelScope.launch {
            repository.deleteWord(word)
        }
    }
}