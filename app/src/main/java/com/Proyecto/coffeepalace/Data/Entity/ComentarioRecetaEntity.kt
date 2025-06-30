package com.Proyecto.coffeepalace.Data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comentarios_receta")
data class ComentarioRecetaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idReceta: Int,
    val idUsuario: Int,
    val text: String,
    val rating: Int,
    val timestamp: Long = System.currentTimeMillis()
)