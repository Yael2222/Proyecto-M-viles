package com.Proyecto.coffeepalace.Model

data class ComentarioProducto (
    val usuarioId: Int,
    val nombreUsuario: String,
    val productoId: Int,
    val titulo: String,
    val texto: String,
    val fechaCreacion: String,
    val meGusta: Int = 0,
)