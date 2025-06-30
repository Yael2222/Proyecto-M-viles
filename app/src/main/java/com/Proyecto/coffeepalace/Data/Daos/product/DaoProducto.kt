package com.Proyecto.coffeepalace.Data.Daos.product

import com.Proyecto.coffeepalace.Data.Model.Producto

interface DaoProducto {
    suspend fun getAllProducts(): List<Producto>
    suspend fun getProductsByCategory(categoryId: Long): List<Producto>
}