package com.example.flashcards.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flashcards.ui.flashcards.FlashcardScreen
import com.example.flashcards.ui.flashcards.FlashcardViewModel
import com.example.flashcards.ui.quiz.QuizScreen
import com.example.flashcards.ui.quiz.QuizViewModel
import com.example.flashcards.ui.vocabulary.VocabularyListScreen
import com.example.flashcards.ui.vocabulary.VocabularyViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val flashcardViewModel: FlashcardViewModel = koinViewModel()
    val vocabularyViewModel: VocabularyViewModel = koinViewModel()
    val quizViewModel: QuizViewModel = koinViewModel()

    NavHost(navController = navController, startDestination = "vocabulary_list") {
        composable("vocabulary_list") {
            VocabularyListScreen(viewModel = vocabularyViewModel, navController = navController)
        }
        composable("flashcard") {
            FlashcardScreen(viewModel = flashcardViewModel)
        }
        composable("quiz") {
            QuizScreen(viewModel = quizViewModel, onBackClick = { navController.popBackStack() })
        }
    }
}
