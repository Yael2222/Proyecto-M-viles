package com.Proyecto.coffeepalace.Data.Model

import com.google.gson.annotations.SerializedName

data class detalle_factura (
    val id: Long? = null,
    @SerializedName("id_factura")
    val idFactura: Long,
    @SerializedName("id_producto")
    val idProducto: Long
)