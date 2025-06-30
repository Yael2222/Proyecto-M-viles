package com.Proyecto.coffeepalace.Data.Model

data class Product(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val imagen: String,
    val precio: Double,
    val categoria: Int
)