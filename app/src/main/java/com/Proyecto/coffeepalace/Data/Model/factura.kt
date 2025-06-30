package com.Proyecto.coffeepalace.Data.Model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class factura(
    val id: Long? = null,

    @SerializedName("fecha")
    val fecha: Date,

    @SerializedName("usuario")
    val usuarioId: Long,

    @SerializedName("total_factura")
    val totalFactura: Double,

    @SerializedName("numero_factura")
    val numeroFactura: String
)