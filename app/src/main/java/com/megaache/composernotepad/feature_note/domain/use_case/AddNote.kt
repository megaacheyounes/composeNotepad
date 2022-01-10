package com.megaache.composernotepad.feature_note.domain.use_case

import com.megaache.composernotepad.feature_note.domain.model.Note
import com.megaache.composernotepad.feature_note.domain.repository.NoteRepository
import java.security.InvalidParameterException

class AddNote(
    val noteRepository: NoteRepository
) {

    @Throws(InvalidParameterException::class)
    suspend operator fun invoke(note: Note)  {
        //todo: add safety tests
        if(note.title.isBlank()){
            throw InvalidParameterException("note title can not be null")
        }
        if(note.content.isBlank()){
            throw InvalidParameterException("note content can not be null")
        }

        noteRepository.insertNote(note)
    }
}