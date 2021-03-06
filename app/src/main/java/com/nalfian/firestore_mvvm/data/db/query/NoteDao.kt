package com.nalfian.firestore_mvvm.data.db.query

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nalfian.firestore_mvvm.data.db.entity.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(note: Note)

    @Query("select * from note")
    fun getNotes(): LiveData<List<Note>>

    @Query("select * from note where date = :date")
    fun checkIsDuplicate(date: Long): List<Note>
}