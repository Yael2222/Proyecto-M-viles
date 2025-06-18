package com.Proyecto.coffeepalace.Data.Model

import kotlinx.serialization.Serializable

@Serializable
data class categoria(
    val id: Long,
    val nombre: String
)
