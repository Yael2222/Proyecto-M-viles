package com.Proyecto.coffeepalace.Data.Model

import kotlinx.serialization.Serializable

import com.google.gson.annotations.SerializedName

@Serializable
data class ingrediente(
    val id: Long? = null,
    @SerializedName("nombre")
    val nombre: String
)