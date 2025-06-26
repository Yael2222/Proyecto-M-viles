package com.Proyecto.coffeepalace.Data.Model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class categoria(
    val id: Long? = null,
    @SerializedName("nombre")
    val nombre: String
)