package com.example.flashcards.ui.vocabulary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashcards.data.model.Category
import com.example.flashcards.data.model.Vocabulary

@Composable
fun EditWordDialog(
    word: Vocabulary,
    onDismiss: () -> Unit,
    onSave: (String, String, Category) -> Unit,
) {
    var koreanWord by remember { mutableStateOf(word.koreanWord) }
    var englishMeaning by remember { mutableStateOf(word.englishMeaning) }
    var selectedCategory by remember { mutableStateOf(word.category) }

    val categories = Category.entries.toTypedArray()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Word") },
        text = {
            Column {
                OutlinedTextField(
                    value = koreanWord,
                    onValueChange = { koreanWord = it },
                    label = { Text("Korean Word") },
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = englishMeaning,
                    onValueChange = { englishMeaning = it },
                    label = { Text("English Meaning") },
                )
                // Dropdown for Category selection
                var expanded by remember { mutableStateOf(false) }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Category: ${selectedCategory.displayName}",
                        modifier =
                            Modifier
                                .clickable { expanded = true }
                                .padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = category.displayName)
                                },
                                onClick = {
                                    selectedCategory = category
                                    expanded = false
                                },
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (koreanWord.isNotBlank() && englishMeaning.isNotBlank()) {
                        onSave(koreanWord, englishMeaning, selectedCategory)
                    }
                },
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewEditWordDialog() {
    EditWordDialog(
        Vocabulary(
            id = 1,
            koreanWord = "사과",
            englishMeaning = "Apple",
            isFavorite = true,
            category = Category.UNKNOWN,
        ),
        {},
        { x, y, z -> },
    )
}
