package com.nalfian.firestore_mvvm.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String? = null,
    val date: Long? = null
)