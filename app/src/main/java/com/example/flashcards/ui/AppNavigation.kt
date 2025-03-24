package com.example.flashcards.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.flashcards.ui.flashcards.FlashcardScreen
import com.example.flashcards.ui.flashcards.FlashcardViewModel
import com.example.flashcards.ui.quiz.GameOverScreen
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
        composable("quiz_screen") {
            QuizScreen(viewModel = quizViewModel, navController = navController)
        }

        composable(
            route = "game_over/{finalScore}",
            arguments = listOf(navArgument("finalScore") { type = NavType.IntType })
        ) { backStackEntry ->
            val finalScore = backStackEntry.arguments?.getInt("finalScore") ?: 0

            GameOverScreen(
                finalScore = finalScore,
                onRestartQuiz = {
                    quizViewModel.restartQuiz()
                    navController.navigate("quiz_screen") { popUpTo("quiz_screen") { inclusive = true } }
                },
                onBackToMain = {
                    navController.popBackStack("vocabulary_list", inclusive = false)
                }
            )
        }
    }
}
