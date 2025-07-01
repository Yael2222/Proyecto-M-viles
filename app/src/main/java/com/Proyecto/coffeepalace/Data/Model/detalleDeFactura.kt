package com.Proyecto.coffeepalace.Data.Model

import kotlinx.serialization.Serializable

@Serializable
data class detalleDeFactura(
    val id: Int,
    val id_factura: Int,
    val id_producto: Int
)