package com.megaache.composernotepad.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
) {
    companion object {
        val COLORS = listOf(
            Color(0xffab47bc),
            Color(0xffef5350),
            Color(0xffb2ff59),
            Color(0xff18ffff),
            Color(0xffe6ee9c)
        )

    }
}