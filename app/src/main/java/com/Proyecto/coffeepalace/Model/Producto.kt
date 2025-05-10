package com.Proyecto.coffeepalace.Model

data class Producto (
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagen: String,
    val categoria: List<String>,

)
