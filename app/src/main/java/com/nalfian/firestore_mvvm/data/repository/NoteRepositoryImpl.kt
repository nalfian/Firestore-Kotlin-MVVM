package com.nalfian.firestore_mvvm.data.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*
import com.nalfian.firestore_mvvm.data.db.entity.Note
import com.nalfian.firestore_mvvm.data.db.query.NoteDao
import com.nalfian.firestore_mvvm.internal.Coroutines
import kotlinx.coroutines.tasks.await
import androidx.lifecycle.MutableLiveData



class NoteRepositoryImpl(
    private val noteDao: NoteDao,
    private val database: FirebaseFirestore
) : NoteRepository {

    private val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()

    override suspend fun getNotes(): LiveData<out List<Note>> {
        database.firestoreSettings = settings
        updateNote()
        return noteDao.getNotes()
    }

    override suspend fun getNoteServer(): LiveData<out List<Note>> {
        val list = MutableLiveData<List<Note>>()
        val notes = ArrayList<Note>()

        database.collection("note").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(querySnapshot: QuerySnapshot?,
                                 e: FirebaseFirestoreException?) {
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen error", e)
                    return
                }
                notes.clear()
                for (document in querySnapshot!!) {
                    notes.add(document.toObject(Note::class.java))
                }
                list.postValue(notes)
                for (change in querySnapshot.documentChanges) {
                    if (change.type == DocumentChange.Type.ADDED) {
                        Log.d(ContentValues.TAG, "data:" + change.document.data)
                    }
                    val source = if (querySnapshot.metadata.isFromCache)
                        "local cache"
                    else
                        "server"
                    Log.d(ContentValues.TAG, "Data fetched from $source")
                }
            }
        })
        return list
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
        database.firestoreSettings = settings
        note.id = database.collection("note").document().id
        database.collection("note")
            .document(note.id)
            .set(note)
            .addOnSuccessListener {}
    }

}