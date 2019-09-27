package com.nalfian.firestore_mvvm.data.repository

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.nalfian.firestore_mvvm.data.db.entity.Note
import com.nalfian.firestore_mvvm.data.db.query.NoteDao
import com.nalfian.firestore_mvvm.internal.Coroutines
import kotlinx.coroutines.tasks.await

class NoteRepositoryImpl(
    private val noteDao: NoteDao,
    private val database: FirebaseFirestore
) : NoteRepository {

    override suspend fun getNotes(): LiveData<out List<Note>> {
        updateNote()
        return noteDao.getNotes()
    }

    private fun updateNote() {
        database.collection("note")
            .addSnapshotListener { querySnapshot, _ ->
                querySnapshot?.let { query ->
                    for (doc in query.documents) {
                        Coroutines.io {
                            val note = doc.toObject(Note::class.java)
                            note?.id = doc.id
                            if (note?.date?.let { noteDao.checkIsDuplicate(it).size } == 0)
                                note.let { noteDao.upsert(it) }
                        }
                    }
                }
            }
    }

    override fun createNote(note: Note) {
        note.id = database.collection("note").document().id
        database.collection("note")
            .document(note.id)
            .set(note)
            .addOnSuccessListener {}
    }

}