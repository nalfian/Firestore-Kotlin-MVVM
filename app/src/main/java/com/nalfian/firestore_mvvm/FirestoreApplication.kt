package com.nalfian.firestore_mvvm

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.nalfian.firestore_mvvm.data.db.FirestoreDatabase
import com.nalfian.firestore_mvvm.data.repository.NoteRepository
import com.nalfian.firestore_mvvm.data.repository.NoteRepositoryImpl
import com.nalfian.firestore_mvvm.ui.main.MainViewFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class FirestoreApplication : Application(), KodeinAware {
    override val kodein: Kodein
        get() = Kodein.lazy {

            bind() from singleton { FirestoreDatabase(instance()) }
            bind() from singleton { instance<FirestoreDatabase>().noteDao() }
            bind() from singleton { FirebaseFirestore.getInstance() }
            bind<NoteRepository>() with singleton {
                NoteRepositoryImpl(instance(), instance())
            }
            bind() from provider { MainViewFactory(instance()) }
        }
}