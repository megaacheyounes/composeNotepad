package com.megaache.composernotepad.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditEvent {
    data class TitleEntered(val title: String) : AddEditEvent()
    data class TitleFocusChange(val focusState: FocusState) : AddEditEvent()
    data class DescriptionEntered(val description: String) : AddEditEvent()
    data class DescriptionFocusChange(val focusState: FocusState) : AddEditEvent()
    data class ColorSet(val color: Int) : AddEditEvent()
    object Save : AddEditEvent()
    object ShowSnackBar : AddEditEvent()

}