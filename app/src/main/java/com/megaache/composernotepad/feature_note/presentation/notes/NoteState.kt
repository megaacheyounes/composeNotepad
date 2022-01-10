package com.megaache.composernotepad.feature_note.presentation.notes

import com.megaache.composernotepad.feature_note.domain.model.Note
import com.megaache.composernotepad.feature_note.domain.util.NoteSort
import com.megaache.composernotepad.feature_note.domain.util.SortType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteSort: NoteSort = NoteSort.Title(SortType.Descending),
    val isOrderControlVisible: Boolean = false
)