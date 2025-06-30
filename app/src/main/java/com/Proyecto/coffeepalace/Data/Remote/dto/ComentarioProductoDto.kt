package com.Proyecto.coffeepalace.Data.Remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ComentarioProductoDto(
    val id: Int = 0,
    val id_producto: Int,
    val id_usuario: Int,
    val texto: String,
    val calificacion: Int
)