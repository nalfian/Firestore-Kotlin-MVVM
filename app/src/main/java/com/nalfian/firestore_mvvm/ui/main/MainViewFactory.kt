package com.nalfian.firestore_mvvm.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nalfian.firestore_mvvm.data.repository.NoteRepository

class MainViewFactory(private val noteRepository: NoteRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(noteRepository) as T
    }
}