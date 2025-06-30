package com.Proyecto.coffeepalace.Data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comentarios_producto")
data class ComentarioProductoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idProducto: Int,
    val idUsuario: Int,
    val text: String,
    val rating: Int,
    val timestamp: Long = System.currentTimeMillis()
)
