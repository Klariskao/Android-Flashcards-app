package com.example.flashcards.ui.vocabulary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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

@Composable
fun AddWordDialog(
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit,
) {
    var koreanWord by remember { mutableStateOf("") }
    var englishMeaning by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Word") },
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
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (koreanWord.isNotBlank() && englishMeaning.isNotBlank()) {
                        onSave(koreanWord, englishMeaning)
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
fun PreviewAddWordDialog() {
    AddWordDialog({}, { x, y -> })
}
