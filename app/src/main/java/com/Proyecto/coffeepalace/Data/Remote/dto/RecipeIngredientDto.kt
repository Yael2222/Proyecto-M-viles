package com.Proyecto.coffeepalace.Data.Remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecetaIngredienteDto(
    val id: Int = 0,
    val id_receta: Int,
    val id_ingrediente: Int
)