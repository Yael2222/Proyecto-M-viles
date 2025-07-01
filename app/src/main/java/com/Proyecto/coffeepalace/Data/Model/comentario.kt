package com.Proyecto.coffeepalace.Data.Model

import kotlinx.serialization.Serializable

@Serializable
data class comentario(
    val id: Int,
    val texto: String,
    val id_usuario: Int,
    val id_producto: Int,
    val calificacion: Int
)