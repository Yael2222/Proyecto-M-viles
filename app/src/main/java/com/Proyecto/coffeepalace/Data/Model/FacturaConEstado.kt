package com.Proyecto.coffeepalace.Data.Model

import kotlinx.serialization.Serializable

@Serializable
data class FacturaConEstado(
    val id: Int,
    val id_factura: Int,
    val estado: String
)