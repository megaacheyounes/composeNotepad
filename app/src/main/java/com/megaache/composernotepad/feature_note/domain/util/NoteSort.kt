package com.megaache.composernotepad.feature_note.domain.util

sealed class NoteSort(
    val sortType: SortType
) {
    fun copy(sortType: SortType): NoteSort {
       return when (this) {
            is Title -> Title(sortType)
            is Timestamp -> Timestamp(sortType)
        }
    }

    class Title(sortType: SortType) : NoteSort(sortType)
    class Timestamp(sortType: SortType) : NoteSort(sortType)
}