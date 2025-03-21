package com.example.flashcards.ui.flashcards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.model.Vocabulary
import com.example.flashcards.repository.VocabularyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlashcardViewModel(
    private val repository: VocabularyRepository,
) : ViewModel() {
    private val _words = MutableStateFlow<List<Vocabulary>>(emptyList())
    val words: StateFlow<List<Vocabulary>> = _words

    init {
        loadWords()
    }

    private fun loadWords() {
        viewModelScope.launch {
            repository.allWords.collect { wordList ->
                _words.value = wordList
            }
        }
    }

    fun markRight(word: Vocabulary) {
        viewModelScope.launch {
            repository.updateWord(word.copy(score = word.score + 1))
        }
    }

    fun markWrong(word: Vocabulary) {
        viewModelScope.launch {
            repository.updateWord(word.copy(score = word.score - 1))
        }
    }
}
