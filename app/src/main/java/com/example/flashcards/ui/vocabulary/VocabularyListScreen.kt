package com.example.flashcards.ui.vocabulary

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.flashcards.data.model.Vocabulary
import com.example.flashcards.repository.VocabularyRepository
import com.example.flashcards.ui.MockVocabularyDao

@Composable
fun VocabularyListScreen(
    viewModel: VocabularyViewModel = viewModel(),
    navController: NavController,
) {
    val words by viewModel.vocabularyList.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedWord by remember { mutableStateOf<Vocabulary?>(null) }

    Scaffold(
        topBar = { VocabularyTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Word")
            }
        },
        bottomBar = {
            Button(
                onClick = { navController.navigate("quiz") },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
            ) {
                Text("Start Quiz")
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
                    onEditClick = { selectedWord = word },
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

    // Show dialog on card long press
    if (selectedWord != null) {
        EditWordDialog(
            word = selectedWord!!,
            onDismiss = { selectedWord = null },
            onSave = { korean, english ->
                viewModel.updateWord(
                    selectedWord!!.copy(
                        koreanWord = korean,
                        englishMeaning = english,
                    ),
                )
                selectedWord = null
            },
        )
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

    VocabularyListScreen(viewModel = mockViewModel, navController = rememberNavController())
}
