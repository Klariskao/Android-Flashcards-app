package com.example.flashcards.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.model.Vocabulary
import com.example.flashcards.repository.VocabularyRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
    private var timerJob: Job? = null
    private val maxQuestions = 10 // End quiz after 10 questions

    init {
        viewModelScope.launch {
            repository.allWords.collect { words ->
                allWords = words
                nextQuestion()
            }
        }
    }

    fun nextQuestion() {
        timerJob?.cancel() // Cancel any existing timer

        if (_quizState.value.questionCount >= maxQuestions) {
            _quizState.value = _quizState.value.copy(isGameOver = true)
            return
        }

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
                    questionCount = _quizState.value.questionCount + 1,
                )

            startTimer()
        }
    }

    fun restartQuiz() {
        _quizState.value = QuizState()
        nextQuestion()
    }

    private fun startTimer() {
        timerJob =
            viewModelScope.launch {
                for (i in 30 downTo 0) {
                    delay(1000L)
                    _quizState.value = _quizState.value.copy(timeLeft = i)

                    if (i == 0) {
                        _quizState.value = _quizState.value.copy(isTimeUp = true)
                        delay(1000L) // Show "Time’s Up!" for 1 sec
                        nextQuestion()
                    }
                }
            }
    }

    fun checkAnswer(selectedAnswer: String) {
        timerJob?.cancel() // Stop the timer if the user answers early

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

        viewModelScope.launch {
            delay(1000L) // Pause for 1 second to show feedback
            nextQuestion()
        }
    }
}
