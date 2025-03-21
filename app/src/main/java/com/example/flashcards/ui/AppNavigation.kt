package com.example.flashcards.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.flashcards.ui.flashcards.FlashcardScreen
import com.example.flashcards.ui.flashcards.FlashcardViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(navController: NavController) {
    val viewModel: FlashcardViewModel = koinViewModel()

    FlashcardScreen(viewModel = viewModel)
}
