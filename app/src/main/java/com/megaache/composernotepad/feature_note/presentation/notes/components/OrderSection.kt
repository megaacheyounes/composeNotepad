package com.megaache.composernotepad.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.megaache.composernotepad.feature_note.domain.util.NoteSort
import com.megaache.composernotepad.feature_note.domain.util.SortType

@Preview("sort section")
@Composable
fun OrderSection(
    onOrderChange: (noteSort: NoteSort) -> Unit = {},
    noteSort: NoteSort = NoteSort.Timestamp(SortType.Descending),
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            //note order
            DefaultRadioButton(
                "Title",
                noteSort is NoteSort.Title,
                { onOrderChange(NoteSort.Title(noteSort.sortType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                "Date",
                noteSort is NoteSort.Timestamp,
                { onOrderChange(NoteSort.Timestamp(noteSort.sortType)) }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            DefaultRadioButton(
                "Ascending",
                noteSort.sortType is SortType.Ascending,
                { onOrderChange(noteSort.copy(SortType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                "Descending",
                noteSort.sortType is SortType.Descending,
                { onOrderChange(noteSort.copy(SortType.Descending)) }
            )


        }
    }

}