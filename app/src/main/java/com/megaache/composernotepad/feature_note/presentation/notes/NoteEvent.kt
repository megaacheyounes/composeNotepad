package com.megaache.composernotepad.feature_note.presentation.notes

import com.megaache.composernotepad.feature_note.domain.model.Note
import com.megaache.composernotepad.feature_note.domain.util.NoteSort

sealed class NoteEvent {
    class DeleteEvent(val note: Note) : NoteEvent()
    object RestoreEvent : NoteEvent()
    object ToggleSortUIControls : NoteEvent()
    class Sort(val noteSort: NoteSort):NoteEvent()
}