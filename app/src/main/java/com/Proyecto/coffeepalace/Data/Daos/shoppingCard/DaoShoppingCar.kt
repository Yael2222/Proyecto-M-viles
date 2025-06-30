package com.Proyecto.coffeepalace.Data.Daos.shoppingCard

import com.Proyecto.coffeepalace.Data.Model.Client.Carrito

interface DaoShoppingCar {
    suspend fun getAllOwnShoppingCar(userId: Long): List<CarritoProductos>
    suspend fun getOwnShoppingCarById(shoppingCarId: Long): CarritoProductos ?
    suspend fun addProductToShoppingCar(productId: Long, clientId: Long): Boolean
    suspend fun deleteProductFromShoppingCar(shoppingCarId: Long): Boolean
}