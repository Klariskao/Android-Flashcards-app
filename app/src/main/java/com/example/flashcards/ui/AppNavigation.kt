package com.example.flashcards.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flashcards.ui.flashcards.FlashcardScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "flashcards") {
        composable("flashcards") { FlashcardScreen() }
    }
}