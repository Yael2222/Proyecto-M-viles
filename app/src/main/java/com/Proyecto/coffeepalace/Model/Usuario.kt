package com.Proyecto.coffeepalace.Model

data class Usuario (
    val id: Int,
    val nombre: String,
    val correo: String,
    val contraseña: String,
    val carrito: List<Producto>,
    )