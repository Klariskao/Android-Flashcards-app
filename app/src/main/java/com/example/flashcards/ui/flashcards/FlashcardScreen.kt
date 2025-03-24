package com.example.flashcards.ui.flashcards

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flashcards.data.model.Category
import com.example.flashcards.data.model.Vocabulary

@Composable
fun FlashcardScreen(viewModel: FlashcardViewModel = viewModel()) {
    val words by viewModel.words.collectAsState(initial = emptyList())

    if (words.isNotEmpty()) {
        var currentIndex by remember { mutableStateOf(0) }
        val word = words[currentIndex]

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Flashcard(word = word)

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Button(
                    onClick = {
                        viewModel.markWrong(word)
                        currentIndex = (currentIndex + 1) % words.size
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("❌ Wrong")
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = {
                        viewModel.markRight(word)
                        currentIndex = (currentIndex + 1) % words.size
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("✅ Right")
                }
            }
        }
    } else {
        Text("No words available! Add some vocabulary first.", textAlign = TextAlign.Center)
    }
}

@Composable
fun Flashcard(word: Vocabulary) {
    var flipped by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "",
    )

    Box(
        modifier =
            Modifier
                .size(250.dp, 150.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                }.shadow(8.dp, shape = RoundedCornerShape(10.dp))
                .clickable { flipped = !flipped }
                .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (rotation < 90f) {
            Text(
                text = word.koreanWord,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
            )
        } else {
            Text(
                text = word.englishMeaning,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier.graphicsLayer {
                        rotationY = 180f
                    },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFlashcard() {
    Flashcard(
        word =
            Vocabulary(
                koreanWord = "사과",
                englishMeaning = "Apple",
                category = Category.UNKNOWN,
            ),
    )
}
