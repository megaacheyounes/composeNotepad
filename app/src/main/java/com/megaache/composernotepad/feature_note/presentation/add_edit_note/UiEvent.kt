package com.megaache.composernotepad.feature_note.presentation.add_edit_note

sealed class UiEvent {
    data class showErrorMessage(val message: String) : UiEvent()
    object NotedSavedSuccessfully : UiEvent()

}