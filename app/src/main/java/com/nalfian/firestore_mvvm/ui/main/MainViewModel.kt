package com.nalfian.firestore_mvvm.ui.main

import androidx.lifecycle.ViewModel
import com.nalfian.firestore_mvvm.data.db.entity.Note
import com.nalfian.firestore_mvvm.data.repository.NoteRepository
import com.nalfian.firestore_mvvm.internal.lazyDeferred

class MainViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val notes by lazyDeferred {
        noteRepository.getNotes()
    }

    fun createNote(note: Note) = lazyDeferred {
        noteRepository.createNote(note)
    }
}
