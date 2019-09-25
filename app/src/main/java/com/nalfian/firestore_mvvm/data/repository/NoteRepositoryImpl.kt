package com.nalfian.firestore_mvvm.data.repository

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.nalfian.firestore_mvvm.data.db.entity.Note
import com.nalfian.firestore_mvvm.data.db.query.NoteDao

class NoteRepositoryImpl(
    private val noteDao: NoteDao,
    private val database: FirebaseFirestore
) : NoteRepository {

    override suspend fun getNotes(): LiveData<out List<Note>> {
        return noteDao.getNotes()
    }

    override suspend fun createNote(note: Note): Boolean {
        database.collection("note")
            .add(note)
            .addOnSuccessListener {

            }
        return true
    }

}