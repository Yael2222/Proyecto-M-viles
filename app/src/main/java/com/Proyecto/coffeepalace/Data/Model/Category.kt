package com.Proyecto.coffeepalace.Data.Model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName // Importa si el nombre de la columna en Supabase difiere del nombre de la propiedad en Kotlin

@Serializable
data class Category (
    val id: Int,
    val nombre_categoria: String,
)