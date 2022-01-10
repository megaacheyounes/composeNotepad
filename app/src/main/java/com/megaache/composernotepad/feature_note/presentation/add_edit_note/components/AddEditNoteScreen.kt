package com.megaache.composernotepad.feature_note.presentation.add_edit_note.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.megaache.composernotepad.feature_note.domain.model.Note
import com.megaache.composernotepad.feature_note.presentation.add_edit_note.AddEditEvent
import com.megaache.composernotepad.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import com.megaache.composernotepad.feature_note.presentation.add_edit_note.UiEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    noteColor: Int
) {
    val scope = rememberCoroutineScope()

    val titleState = viewModel.titleState
    val contentState = viewModel.descriptionState

    val scaffoldState = rememberScaffoldState()
    val noteColorBackgroundAnimation = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.colorState.value)
        )
    }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvent.NotedSavedSuccessfully -> {
                    navController.navigateUp()
                }
                is UiEvent.showErrorMessage -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = uiEvent.message
                    )
                }
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditEvent.Save)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "save")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(noteColorBackgroundAnimation.value)
                .padding(8.dp),

            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Note.COLORS.forEach {
                    val colorInt = it.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(it, shape = CircleShape)
                            .shadow(20.dp, CircleShape)
                            .border(
                                width = 3.dp,
                                //todo: handle case where color ($it) is black
                                color = Color.Black,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteColorBackgroundAnimation.animateTo(
                                        targetValue = it,
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditEvent.ColorSet(colorInt))
                            }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            DefaultTextField(
                text = titleState.value.text,
                hint = titleState.value.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditEvent.TitleEntered(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditEvent.TitleFocusChange(it))
                },
                isHintVisible = titleState.value.isHintVisible,
                textStyle = MaterialTheme.typography.h6,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            DefaultTextField(
                text = contentState.value.text,
                hint = contentState.value.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditEvent.DescriptionEntered(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditEvent.DescriptionFocusChange(it))
                },
                isHintVisible = contentState.value.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                singleLine = false
            )
        }
    }

}