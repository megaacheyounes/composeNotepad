package com.megaache.composernotepad.feature_note.domain.util

sealed class SortType {
    object Ascending:SortType()
    object Descending:SortType()
}