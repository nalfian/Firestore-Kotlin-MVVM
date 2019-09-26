package com.nalfian.firestore_mvvm.data.db.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey
    @NonNull
    var id: String,
    val name: String? = null,
    val date: Long? = null
)