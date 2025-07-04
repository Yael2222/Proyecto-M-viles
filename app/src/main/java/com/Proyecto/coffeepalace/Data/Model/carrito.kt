package com.Proyecto.coffeepalace.Data.Model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class carrito(
    val id: Long? = null,
    @SerializedName("id_producto")
    val id_producto: String,
    @SerializedName("id_usuario")
    val id_usuario: String
)