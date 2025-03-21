package com.example.flashcards.ui.vocabulary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flashcards.data.model.Vocabulary
import com.example.flashcards.repository.VocabularyRepository
import com.example.flashcards.ui.MockVocabularyDao

@Composable
fun VocabularyListScreen(viewModel: VocabularyViewModel = viewModel()) {
    val words by viewModel.vocabularyList.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { VocabularyTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Word")
            }
        },
    ) { padding ->
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            items(words) { word ->
                VocabularyItem(
                    word = word,
                    onFavoriteClick = { viewModel.toggleFavorite(word) },
                    onDeleteClick = { viewModel.deleteWord(word) },
                )
            }
        }
    }

    // Show dialog when "Add Word" button is clicked
    if (showDialog) {
        AddWordDialog(
            onDismiss = { showDialog = false },
            onSave = { korean, english ->
                viewModel.addWord(korean, english)
                showDialog = false
            },
        )
    }
}

@Composable
fun VocabularyItem(
    word: Vocabulary,
    onFavoriteClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = word.koreanWord,
                    fontWeight = FontWeight.Bold,
                )
                Text(text = word.englishMeaning, color = Color.Gray)
            }
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (word.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (word.isFavorite) Color.Red else Color.Gray,
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Gray,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VocabularyTopBar() {
    TopAppBar(
        title = { Text("Vocabulary List") },
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewVocabularyListScreen() {
    val mockViewModel = VocabularyViewModel(VocabularyRepository(MockVocabularyDao()))

    VocabularyListScreen(viewModel = mockViewModel)
}
