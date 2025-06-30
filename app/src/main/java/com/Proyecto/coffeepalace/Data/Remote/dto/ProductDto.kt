package com.Proyecto.coffeepalace.Data.Remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val imagen: String,
    val precio: Double,
    val categoria: Int
)