package com.Proyecto.coffeepalace.Data.Model

data class FacturaConNombreUsuario(
    val factura: factura,
    val nombreUsuario: String,
    val estadoOrden: String,
    val productos: List<producto>
)