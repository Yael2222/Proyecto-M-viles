package com.Proyecto.coffeepalace.Data.Model

import kotlinx.serialization.Serializable

@Serializable
data class producto(
    val id: Long = 0,
    val nombre: String,
    val descripcion: String,
    val imagen: String,
    val precio: Double,
    val categoria: Long
)