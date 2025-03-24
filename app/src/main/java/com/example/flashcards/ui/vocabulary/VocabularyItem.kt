package com.example.flashcards.ui.vocabulary

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashcards.data.model.Category
import com.example.flashcards.data.model.Vocabulary

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VocabularyItem(
    word: Vocabulary,
    onFavoriteClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: (Vocabulary) -> Unit,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .combinedClickable(
                    onClick = {}, // Empty to prevent conflicts
                    onLongClick = { onEditClick(word) }, // Long press to edit
                ),
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
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Score: ${word.score}",
                fontSize = 14.sp,
                color = Color.Gray,
            )
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

@Preview(showBackground = true)
@Composable
fun PreviewVocabularyItem() {
    VocabularyItem(
        Vocabulary(
            id = 1,
            koreanWord = "사과",
            englishMeaning = "Apple",
            isFavorite = true,
            category = Category.VERBS,
        ),
        {},
        {},
        { x -> },
    )
}
