package com.megaache.composernotepad.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

@Preview("default text field")
@Composable
fun DefaultTextField(
    text: String = "",
    hint: String = "hint",
    isHintVisible: Boolean = false,
    singleLine: Boolean = true,
    textStyle: TextStyle = TextStyle(),
    onValueChange: (String) -> Unit = {},
    onFocusChange: (FocusState) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            textStyle = textStyle,
            singleLine = singleLine,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { onFocusChange(it) }
        )

        if (isHintVisible)
            Text(
                text = hint,
                color = Color.DarkGray,
                style = MaterialTheme.typography.subtitle2
            )
    }
}