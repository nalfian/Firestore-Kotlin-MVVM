package com.nalfian.firestore_mvvm.data.db.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude

@Entity
data class Note(
    val name: String? = null,
    val date: Long? = null
){
    @PrimaryKey
    @get:Exclude
    var id: String = ""
}