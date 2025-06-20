package com.Proyecto.coffeepalace.Data.Model

data class Product(
    val title: String,
    val imageUrl: String,
    val price: Double,
    val rating: Double,
    val reviews: Int,
    val description: String
)
