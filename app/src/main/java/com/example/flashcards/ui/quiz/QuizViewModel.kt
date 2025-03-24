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
    private var currentWord: Vocabulary? = null
    private var isQuizStarted = false // Flag to track if the quiz has started

    init {
        // Initialize words when repository sends them but don't start the quiz here.
        viewModelScope.launch {
            repository.allWords.collect { words ->
                allWords = words
            }
        }
    }

    // Start the quiz (called by UI)
    fun startQuiz() {
        if (!isQuizStarted) {
            isQuizStarted = true
            nextQuestion()
        }
    }

    fun nextQuestion() {
        timerJob?.cancel() // Cancel any existing timer

        if (_quizState.value.questionCount >= maxQuestions) {
            _quizState.value = _quizState.value.copy(isGameOver = true)
            return
        }

        if (allWords.isNotEmpty()) {
            currentWord = allWords.random()
            val isKoreanToEnglish =
                Random.nextBoolean() // 50% chance of Korean → English or vice versa

            val correctAnswer =
                if (isKoreanToEnglish) currentWord!!.englishMeaning else currentWord!!.koreanWord
            val incorrectAnswers =
                allWords
                    .filter { it.id != currentWord!!.id }
                    .shuffled()
                    .take(5) // Select 5 random incorrect answers
                    .map { if (isKoreanToEnglish) it.englishMeaning else it.koreanWord }

            _quizState.value =
                _quizState.value.copy(
                    currentWord = if (isKoreanToEnglish) currentWord!!.koreanWord else currentWord!!.englishMeaning,
                    correctAnswer = correctAnswer,
                    options = (incorrectAnswers + correctAnswer).shuffled(), // Shuffle options
                    selectedAnswer = null,
                    isKoreanToEnglish = isKoreanToEnglish,
                    questionCount = _quizState.value.questionCount + 1,
                )

            startTimer()
        }
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
                        nextQuestion() // Move to the next question after time is up
                    }
                }
            }
    }

    // Call this function when an answer is selected
    fun checkAnswer(selectedAnswer: String) {
        timerJob?.cancel() // Stop the timer if the user answers early

        val isCorrect = selectedAnswer == _quizState.value.correctAnswer
        val updatedScore = if (isCorrect) _quizState.value.score + 1 else _quizState.value.score

        viewModelScope.launch {
            currentWord?.let { word ->
                if (isCorrect) {
                    repository.increaseScore(word.id)
                } else {
                    repository.decreaseScore(word.id)
                }
            }

            _quizState.value =
                _quizState.value.copy(
                    selectedAnswer = selectedAnswer,
                    score = updatedScore,
                )
        }

        // Wait 1 second before going to the next question
        viewModelScope.launch {
            delay(1000)
            nextQuestion() // Trigger next question after a delay
        }
    }

    // Restart the quiz
    fun restartQuiz() {
        _quizState.value = QuizState()
        isQuizStarted = false
        startQuiz() // Start the quiz over after resetting
    }
}
