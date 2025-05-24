package com.Proyecto.coffeepalace.Data.Model

data class ComentarioReceta (
    val usuarioId: Int,
    val nombreUsuario: String,
    val RecetaId: Int,
    val titulo: String,
    val texto: String,
    val fechaCreacion: String,
    val meGusta: Int = 0,
)