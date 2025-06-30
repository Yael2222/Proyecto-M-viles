package com.Proyecto.coffeepalace.Data.Model

data class Recipe(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val instrucciones: String,
    val imagen: String
)

/*data class Recipe(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val ingredients: List<String>,
    val instructions: String,
    val rating: Float,
    val ratingCount: Int,
    val comments: List<Comment> = emptyList()
)*/

