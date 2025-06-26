package com.Proyecto.coffeepalace.Data.Model

import kotlinx.serialization.Serializable

@Serializable
data class receta_ingrediente(
    val id: Long = 0,
    val id_ingrediente: Long,
    val id_receta: Long
)
