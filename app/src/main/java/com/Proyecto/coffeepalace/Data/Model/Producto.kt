package com.Proyecto.coffeepalace.Data.Model

data class Producto (
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagen: String,
    val categoria: List<String>,

)
