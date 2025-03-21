package com.example.flashcards.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.flashcards.ui.flashcards.FlashcardScreen
import com.example.flashcards.ui.flashcards.FlashcardViewModel
import com.example.flashcards.ui.quiz.QuizScreen
import com.example.flashcards.ui.quiz.QuizViewModel
import com.example.flashcards.ui.vocabulary.VocabularyListScreen
import com.example.flashcards.ui.vocabulary.VocabularyViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(navController: NavController) {
    val flashcardViewModel: FlashcardViewModel = koinViewModel()
    val vocabularyViewModel: VocabularyViewModel = koinViewModel()
    val quizViewModel: QuizViewModel = koinViewModel()

    FlashcardScreen(viewModel = flashcardViewModel)
    VocabularyListScreen(viewModel = vocabularyViewModel, navController = navController)
    QuizScreen(viewModel = quizViewModel, onBackClick = { navController.popBackStack() })
}
