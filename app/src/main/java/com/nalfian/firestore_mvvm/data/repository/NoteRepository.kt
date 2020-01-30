package com.nalfian.firestore_mvvm.data.repository

import androidx.lifecycle.LiveData
import com.nalfian.firestore_mvvm.data.db.entity.Note

interface NoteRepository {
    
    suspend fun getNotes(): LiveData<out List<Note>>

    suspend fun getNoteServer(): LiveData<out List<Note>>

    fun createNote(note: Note)

}