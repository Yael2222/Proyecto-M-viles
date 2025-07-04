package com.Proyecto.coffeepalace.Data.Model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class orden_vendedor (
    val id: Long? = null,
    @SerializedName("id_factura")
    val idFactura: Long, // Usa camelCase para Kotlin, mapea con SerializedName
    @SerializedName("estado")
    val estado: String
)