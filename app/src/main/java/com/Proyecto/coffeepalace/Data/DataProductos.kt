package com.Proyecto.coffeepalace.Data

import com.Proyecto.coffeepalace.Model.ComentarioProducto
import com.Proyecto.coffeepalace.Model.Producto

class DataProductos {
    val productos = listOf(
        Producto(
            id = 1,
            nombre = "Pizza Loca",
            descripcion = "Pizza deliciosa con ingredientes frescos",
            precio = 1.20,
            imagen = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s",
            categoria = listOf("comida xd"),

        ),
        Producto(
            id = 2,
            nombre = "Pizza Loca",
            descripcion = "Pizza deliciosa con ingredientes frescos",
            precio = 1.20,
            imagen = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s",
            categoria = listOf("Comida Italiana"),

        )
    )
}