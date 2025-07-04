package com.Proyecto.coffeepalace.Data.Daos.product


import com.Proyecto.coffeepalace.Data.Model.Categoria
import com.Proyecto.coffeepalace.Data.Model.Producto

interface DaoProducto {
    suspend fun addProducto(producto: Producto): Boolean
    suspend fun getCategoriasProducto(): List<Categoria>
    suspend fun getProductos(): List<Producto>
    suspend fun deleteProducto(id: Long?): Boolean

    suspend fun getAllProducts(): List<Producto>
    suspend fun getProductsByCategory(categoryId: Long): List<Producto>
}