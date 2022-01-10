package com.megaache.composernotepad.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.megaache.composernotepad.feature_note.domain.model.Note
import com.megaache.composernotepad.feature_note.domain.use_case.NoteUseCases
import com.megaache.composernotepad.feature_note.domain.util.NoteSort
import com.megaache.composernotepad.feature_note.domain.util.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val useCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var lastDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteSort.Timestamp(SortType.Descending))
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.Sort -> {
                if (_state.value.noteSort::class == event.noteSort::class &&
                    _state.value.noteSort.sortType == event.noteSort.sortType
                ) {
                    //same filter, means same result there we do not need to get new list of notes
                    return
                }
                getNotes(event.noteSort)

            }
            is NoteEvent.DeleteEvent -> {
                viewModelScope.launch {
                    useCases.deleteNote(event.note)
                    lastDeletedNote = event.note
                }
            }
            is NoteEvent.RestoreEvent -> {
                lastDeletedNote?.let { lastDeletedNote ->
                    viewModelScope.launch {
                        useCases.addNote(lastDeletedNote)
                    }
                }
                lastDeletedNote = null
            }
            is NoteEvent.ToggleSortUIControls -> {
                _state.value = _state.value.copy(
                    isOrderControlVisible = !_state.value.isOrderControlVisible
                )
            }
        }
    }

    private fun getNotes(noteSort: NoteSort) {
        getNotesJob?.cancel()
        getNotesJob = useCases.getNotes.invoke(noteSort)
            .onEach {
                _state.value = _state.value.copy(
                    noteSort = noteSort,
                    notes = it
                )
            }.launchIn(viewModelScope)
    }

}