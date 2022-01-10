package com.megaache.composernotepad.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.megaache.composernotepad.feature_note.domain.model.Note

@Preview(name = "note item")
@Composable
fun NoteItem(
    note: Note = Note(
        1, "lorem ipsum", "Content ",
        0,
        Color.LightGray.toArgb()
    ),
    modifier: Modifier = Modifier,
    onDeleteClick:()->Unit={}
) {
    Row(
        modifier = modifier,
    ) {
        Card(
            elevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            backgroundColor = Color(note.color)
        ) {
            Column(
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 4.dp)
            ) {

                Text(
                    text = note.title,
                    style = MaterialTheme.typography.body1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = note.content,
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 5
                )
                IconButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = onDeleteClick,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "delete",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}