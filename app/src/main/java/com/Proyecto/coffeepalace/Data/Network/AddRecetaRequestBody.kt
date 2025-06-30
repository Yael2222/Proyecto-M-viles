package com.Proyecto.coffeepalace.Data.Network

import com.Proyecto.coffeepalace.Data.Model.receta
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
@Serializable
data class AddRecetaRequestBody(
    @SerializedName("recetaData")
    val recetaData: receta, // El objeto receta
    @SerializedName("ingredientesIds")
    val ingredientesIds: List<Long> // Lista de IDs de ingredientes
)