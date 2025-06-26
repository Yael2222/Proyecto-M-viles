package com.Proyecto.coffeepalace.Data.Model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class producto(
    val id: Long? = null,
    val nombre: String,
    val descripcion: String,
    val imagen: String,
    val precio: Double,
    @SerializedName("categoria")
    val categoria: Long
)