package com.nalfian.firestore_mvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nalfian.firestore_mvvm.data.db.entity.Note
import com.nalfian.firestore_mvvm.data.db.query.NoteDao


@Database(
    entities = [Note::class],
    version = 1
)
abstract class FirestoreDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var instance: FirestoreDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FirestoreDatabase::class.java, "firestore.db"
            ).build()
    }
}