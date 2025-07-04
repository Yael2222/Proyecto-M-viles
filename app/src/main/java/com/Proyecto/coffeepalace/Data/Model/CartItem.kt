package com.Proyecto.coffeepalace.Data.Model


import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val productId: Long, // The actual Long ID of the product (from 'producto' table)
    val productName: String,
    val productPrice: Double,
    val productImageUrl: String?,
    var quantity: Int, // This will be the *aggregated* count
    val cartEntryIds: MutableList<Long> // NEW: Keep track of the specific 'carrito' IDs (rows) that make up this aggregated item
)
