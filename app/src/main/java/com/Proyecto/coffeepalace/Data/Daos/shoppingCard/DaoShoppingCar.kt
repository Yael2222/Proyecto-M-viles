package com.Proyecto.coffeepalace.Data.Daos.shoppingCard

import com.Proyecto.coffeepalace.Data.Model.Client.Carrito

interface DaoShoppingCar {
    suspend fun getAllOwnShoppingCar(): List<Carrito>
    suspend fun addProductToShoppingCar(productId: Int): Boolean
    suspend fun deleteProductFromShoppingCar(productId: Int): Boolean
}