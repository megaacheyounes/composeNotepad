package com.megaache.composernotepad.feature_note.presentation.add_edit_note

data class EditTextState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)