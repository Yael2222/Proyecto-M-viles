package com.Proyecto.coffeepalace.Data.Model

import kotlinx.serialization.Serializable

@Serializable
data class ads (
    val id: Long,
    val descripcion: String,
    val imagen: String,
    val titulo: String,
)
