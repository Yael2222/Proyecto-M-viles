package com.Proyecto.coffeepalace.Model

data class ProductoDTO (
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: List<String>,
    val rating: Int? = null,
    val imageRes: String
)