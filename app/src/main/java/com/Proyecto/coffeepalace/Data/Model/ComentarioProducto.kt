package com.Proyecto.coffeepalace.Data.Model

data class ComentarioProducto (
    val usuarioId: Int,
    val nombreUsuario: String,
    val productoId: Int,
    val titulo: String,
    val texto: String,
    val fechaCreacion: String,
    val meGusta: Int = 0,
)