package com.Proyecto.coffeepalace.Data.Model.Client

import kotlinx.serialization.Serializable

@Serializable
data class Carrito(
    val id: Int,
    val id_producto: Int,
    val id_usuario: Int
)
