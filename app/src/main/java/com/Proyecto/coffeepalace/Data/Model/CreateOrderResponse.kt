package com.Proyecto.coffeepalace.Data.Model

import com.google.gson.annotations.SerializedName

data class Invoice(
    val id: Int,
    val fecha: String,
    @SerializedName("usuarioId") // Usar SerializedName si el nombre en JSON es diferente al de Kotlin
    val userId: Int,
    @SerializedName("total_factura")
    val totalAmount: Double,
    @SerializedName("numero_factura")
    val invoiceNumber: String
)

data class CreateOrderResponse(
    val message: String,
    val invoice: Invoice // Ahora es un objeto Invoice, no un String
)