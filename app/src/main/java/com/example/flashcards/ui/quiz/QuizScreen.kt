package com.example.flashcards.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.flashcards.repository.VocabularyRepository
import com.example.flashcards.ui.MockVocabularyDao

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: QuizViewModel = viewModel(),
    navController: NavController,
) {
    val quizState by viewModel.quizState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startQuiz()
    }

    if (quizState.isGameOver) {
        LaunchedEffect(Unit) {
            navController.navigate("game_over/${quizState.score}")
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Timed Quiz") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                )
            },
        ) { padding ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                // Timer
                Text(
                    text = "Time Left: ${quizState.timeLeft}s",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (quizState.timeLeft <= 5) Color.Red else Color.Black,
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Time’s Up Message
                if (quizState.isTimeUp) {
                    Text(
                        text = "Time’s Up!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Text(
                    text = "What is the translation of:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = quizState.currentWord,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.height(16.dp))

                quizState.options.forEach { option ->
                    Button(
                        onClick = { viewModel.checkAnswer(option) },
                        modifier = Modifier.fillMaxWidth(),
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    when {
                                        quizState.selectedAnswer == option && option == quizState.correctAnswer -> Color.Green
                                        quizState.selectedAnswer == option && option != quizState.correctAnswer -> Color.Red
                                        quizState.correctAnswerShown && option == quizState.correctAnswer -> Color.Green
                                        else -> MaterialTheme.colorScheme.primaryContainer
                                    },
                            ),
                    ) {
                        Text(option, color = Color.Black, fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Score: ${quizState.score}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { viewModel.nextQuestion() }) {
                    Text("Next Question")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQuizScreen() {
    val mockViewModel = QuizViewModel(VocabularyRepository(MockVocabularyDao()))

    QuizScreen(mockViewModel, navController = rememberNavController())
}
