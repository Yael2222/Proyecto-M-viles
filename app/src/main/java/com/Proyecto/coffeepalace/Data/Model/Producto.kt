package com.Proyecto.coffeepalace.Data.Model

import kotlinx.serialization.Serializable

@Serializable
data class Producto(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagen: String,
    val categoria: Long,

    )