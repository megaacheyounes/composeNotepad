package com.megaache.composernotepad.feature_note.domain.use_case

import com.megaache.composernotepad.feature_note.domain.model.Note
import com.megaache.composernotepad.feature_note.domain.repository.NoteRepository
import com.megaache.composernotepad.feature_note.domain.util.NoteSort
import com.megaache.composernotepad.feature_note.domain.util.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {

    operator fun invoke(
        noteSort: NoteSort = NoteSort.Title(SortType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map {
            when (noteSort.sortType) {
                SortType.Ascending -> {
                    when (noteSort) {
                        is NoteSort.Title -> it.sortedBy { note -> note.title.lowercase() }
                        is NoteSort.Timestamp -> it.sortedBy { note -> note.timestamp }
                    } //when: noteOrder
                }// OrderType.Descending
                SortType.Descending -> {
                    when (noteSort) {
                        is NoteSort.Title -> it.sortedByDescending { note -> note.title.lowercase() }
                        is NoteSort.Timestamp -> it.sortedByDescending { note -> note.timestamp }
                    } //when: noteOrder
                }//OrderType.Ascending
            }//when: noteOrder.orderType
        }
    }

}