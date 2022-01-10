package com.megaache.composernotepad.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.megaache.composernotepad.feature_note.domain.model.Note
import com.megaache.composernotepad.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.security.InvalidParameterException
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    val useCases: NoteUseCases,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _title = mutableStateOf(
        EditTextState(
            hint = "Enter title..."
        )
    )
    val titleState: State<EditTextState> = _title

    private val _description = mutableStateOf(
        EditTextState(
            hint = "Enter description..."
        )
    )

    val descriptionState: State<EditTextState> = _description

    private val _color = mutableStateOf(Note.COLORS.random().toArgb())
    val colorState: State<Int> = _color

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    /**
     * used to restore deleted note
     */
    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    useCases.getNote(noteId)?.let { note ->
                        currentNoteId = note.id
                        _title.value = _title.value.copy(
                            text = note.title,
                            isHintVisible = note.title.isBlank()
                        )
                        _description.value = _description.value.copy(
                            text = note.content,
                            isHintVisible = note.content.isBlank()
                        )
                        _color.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.TitleEntered -> {
                _title.value = _title.value.copy(
                    text = event.title
                )
            }
            is AddEditEvent.TitleFocusChange -> {
                _title.value = _title.value.copy(
                    isHintVisible = !event.focusState.isFocused && _title.value.text.isBlank()
                )
            }
            is AddEditEvent.DescriptionEntered -> {
                _description.value = _description.value.copy(
                    text = event.description
                )
            }
            is AddEditEvent.DescriptionFocusChange -> {
                _description.value = _description.value.copy(
                    isHintVisible = !event.focusState.isFocused && _description.value.text.isBlank()
                )
            }
            is AddEditEvent.ColorSet -> {
                _color.value = event.color
            }
            is AddEditEvent.Save -> {
                viewModelScope.launch {
                    try {
                        useCases.addNote(
                            Note(
                                title = _title.value.text,
                                content = _description.value.text,
                                color = _color.value,
                                timestamp = System.currentTimeMillis(),
                                id = currentNoteId
                            )
                        )
                        _uiEvent.emit(UiEvent.NotedSavedSuccessfully)
                    } catch (e: InvalidParameterException) {
                        _uiEvent.emit(UiEvent.showErrorMessage(e.toString()))
                    }
                }
            }

        }

    }

}