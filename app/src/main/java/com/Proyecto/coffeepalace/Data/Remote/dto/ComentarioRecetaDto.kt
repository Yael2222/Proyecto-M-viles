package com.Proyecto.coffeepalace.Data.Remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ComentarioRecetaDto(
    val id: Int = 0,
    val id_receta: Int,
    val id_usuario: Int,
    val texto: String,
    val calificacion: Int,
    val fecha_creacion: String? = null
)