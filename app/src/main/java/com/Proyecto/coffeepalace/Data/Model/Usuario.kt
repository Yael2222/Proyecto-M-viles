package com.Proyecto.coffeepalace.Data.Model

import kotlinx.serialization.Serializable

@Serializable
data class usuario (
    val id: Int,
    val nombre: String?,
    val correo: String,
    val imagen: String?,
    val rol: String,
    val auth_id:String
)