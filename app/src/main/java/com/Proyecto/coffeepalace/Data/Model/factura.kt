package com.Proyecto.coffeepalace.Data.Model

import kotlinx.serialization.Serializable

@Serializable
data class factura(
    val id: Int,
    val fecha: String,
    val usuarioId: Int,
    val total_factura: Float,
    val numero_factura: String
)
