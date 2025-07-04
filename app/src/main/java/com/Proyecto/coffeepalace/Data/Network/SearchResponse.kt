package com.Proyecto.coffeepalace.Data.Network

import com.Proyecto.coffeepalace.Data.Model.producto
import com.Proyecto.coffeepalace.Data.Model.receta
import kotlinx.serialization.Serializable // Si estás usando Kotlin Serialization

@Serializable // Asegúrate de tener la anotación correcta si usas Kotlin Serialization o @SerializedName si usas Gson
data class SearchResponse(
    val products: List<producto>,
    val recipes: List<receta>
)