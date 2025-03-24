package com.example.flashcards.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameOverScreen(
    finalScore: Int,
    onRestartQuiz: () -> Unit,
    onBackToMain: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Game Over!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Final Score: $finalScore",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onRestartQuiz, modifier = Modifier.fillMaxWidth()) {
            Text("Restart Quiz")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBackToMain, modifier = Modifier.fillMaxWidth()) {
            Text("Back to Main Menu")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGameOverScreen() {
    GameOverScreen(finalScore = 10, onRestartQuiz = {}, onBackToMain = {})
}
