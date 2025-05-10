package com.Proyecto.coffeepalace.Data

import com.Proyecto.coffeepalace.Model.Producto
import com.Proyecto.coffeepalace.Model.Usuario

class DataUsers {
    val usuarios = listOf(
        Usuario(
            id = 1,
            nombre = "Pizza Loca",
            correo = "Pizza deliciosa con ingredientes frescos",
            contraseña = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s",
            carrito = listOf(
                Producto(
                    id = 1,
                    nombre = "Pizza Loca",
                    descripcion = "Pizza deliciosa con ingredientes frescos",
                    precio = 1.20,
                    imagen = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s",
                    categoria = listOf("comida xd"),
                ),
                Producto(
                    id = 1,
                    nombre = "Pizza Loca",
                    descripcion = "Pizza deliciosa con ingredientes frescos",
                    precio = 1.20,
                    imagen = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s",
                    categoria = listOf("comida xd"),
                )
            )
        ),
        Usuario(
            id = 1,
            nombre = "Pizza Loca",
            correo = "Pizza deliciosa con ingredientes frescos",
            contraseña = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s",
            carrito = listOf(
                Producto(
                    id = 1,
                    nombre = "Pizza Loca",
                    descripcion = "Pizza deliciosa con ingredientes frescos",
                    precio = 1.20,
                    imagen = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s",
                    categoria = listOf("comida xd"),
                ),
                Producto(
                    id = 1,
                    nombre = "Pizza Loca",
                    descripcion = "Pizza deliciosa con ingredientes frescos",
                    precio = 1.20,
                    imagen = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s",
                    categoria = listOf("comida xd"),
                )

            )
        )
    )
}