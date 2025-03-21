package com.example.flashcards.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.flashcards.ui.flashcards.FlashcardScreen
import com.example.flashcards.ui.flashcards.FlashcardViewModel
import com.example.flashcards.ui.vocabulary.VocabularyListScreen
import com.example.flashcards.ui.vocabulary.VocabularyViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(navController: NavController) {
    val flashcardViewModel: FlashcardViewModel = koinViewModel()
    val vocabularyViewModel: VocabularyViewModel = koinViewModel()

    FlashcardScreen(viewModel = flashcardViewModel)
    VocabularyListScreen(viewModel = vocabularyViewModel)
}
