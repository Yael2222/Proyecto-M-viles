package com.Proyecto.coffeepalace.Data.Model.Client

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val id: Long,
    val nombre: String,
    val correo: String,
    val imagen: String,
    val rol: String,
    val auth_id: String

)
//    val address: String,
//    val cellphone: String