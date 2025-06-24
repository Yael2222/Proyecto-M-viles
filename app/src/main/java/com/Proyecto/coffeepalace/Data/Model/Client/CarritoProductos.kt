package com.Proyecto.coffeepalace.Data.Model.Client

import com.Proyecto.coffeepalace.Data.Model.Producto
import kotlinx.serialization.Serializable

@Serializable
data class CarritoProductos(
    val id: Int,
    val id_cliente: Int,
    val producto: Producto
)