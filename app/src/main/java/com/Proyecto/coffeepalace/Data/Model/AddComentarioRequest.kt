package com.Proyecto.coffeepalace.Data.Model

data class AddComentarioRequest(
    val id_producto: Int,
    val texto: String,
    val calificacion: Int
)