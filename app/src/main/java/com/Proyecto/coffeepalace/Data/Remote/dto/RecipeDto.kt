package com.Proyecto.coffeepalace.Data.Remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val instrucciones: String,
    val imagen: String
)