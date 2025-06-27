package com.Proyecto.coffeepalace.Data.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    //val userName: String,
    val text: String,
    val rating: Int,
    //val likes: Int
)
