package com.example.flashcards.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.model.Vocabulary
import com.example.flashcards.repository.VocabularyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class QuizViewModel(
    private val repository: VocabularyRepository,
) : ViewModel() {
    private val _quizState = MutableStateFlow(QuizState())
    val quizState: StateFlow<QuizState> = _quizState

    private var allWords: List<Vocabulary> = emptyList()

    init {
        viewModelScope.launch {
            repository.allWords.collect { words ->
                allWords = words
                nextQuestion()
            }
        }
    }

    fun nextQuestion() {
        if (allWords.isNotEmpty()) {
            val randomWord = allWords.random()
            val isKoreanToEnglish =
                Random.nextBoolean() // 50% chance of Korean → English or vice versa

            val correctAnswer =
                if (isKoreanToEnglish) randomWord.englishMeaning else randomWord.koreanWord
            val incorrectAnswers =
                allWords
                    .filter { it.id != randomWord.id }
                    .shuffled()
                    .take(5) // Select 5 random incorrect answers
                    .map { if (isKoreanToEnglish) it.englishMeaning else it.koreanWord }

            _quizState.value =
                QuizState(
                    currentWord = if (isKoreanToEnglish) randomWord.koreanWord else randomWord.englishMeaning,
                    correctAnswer = correctAnswer,
                    options = (incorrectAnswers + correctAnswer).shuffled(), // Shuffle options
                    isKoreanToEnglish = isKoreanToEnglish,
                )
        }
    }

    fun checkAnswer(selectedAnswer: String) {
        _quizState.value =
            _quizState.value.copy(
                selectedAnswer = selectedAnswer,
                score = if (selectedAnswer == _quizState.value.correctAnswer) {
                    _quizState.value.score + 1
                }
                else {
                    _quizState.value.score
                },
            )
    }
}
