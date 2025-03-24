package com.example.flashcards.ui.quiz

data class QuizState(
    val currentWord: String = "",
    val correctAnswer: String = "",
    val options: List<String> = emptyList(),
    val selectedAnswer: String? = null,
    val isKoreanToEnglish: Boolean = true,
    val score: Int = 0,
    val timeLeft: Int = 30,
    val isTimeUp: Boolean = false,
)
