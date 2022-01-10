package com.megaache.composernotepad.feature_note.presentation.notes.components

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.megaache.composernotepad.feature_note.presentation.notes.NoteEvent
import com.megaache.composernotepad.feature_note.presentation.notes.NoteViewModel
import com.megaache.composernotepad.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun NotesScreen(
    nav: NavController,
    viewModel: NoteViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val state = viewModel.state.value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    nav.navigate(Screen.AddEditNoteScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Your Note",
                    style = MaterialTheme.typography.h4
                )

                IconButton(
                    onClick = { viewModel.onEvent(NoteEvent.ToggleSortUIControls) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "sort"
                    )
                }
            }

            AnimatedVisibility(
                visible = state.isOrderControlVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    noteSort = state.noteSort,
                    onOrderChange = { viewModel.onEvent(NoteEvent.Sort(it)) })
            }
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                nav.navigate(Screen.AddEditNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}")
                            },
                        onDeleteClick = {
                            viewModel.onEvent(NoteEvent.DeleteEvent(note))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    "Note deleted",
                                    actionLabel = "undo"
                                )

                                if (result == SnackbarResult.ActionPerformed) {
                                    //user clicked the button "undo"
                                    viewModel.onEvent(NoteEvent.RestoreEvent)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
        }

    }
}

