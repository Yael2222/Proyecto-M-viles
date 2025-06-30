package com.Proyecto.coffeepalace.Data.Model

data class Anuncio(
    val id: Int,
    val titulo: String,
    val imagen: String,
    val descripcion: String = "Promoción"
)