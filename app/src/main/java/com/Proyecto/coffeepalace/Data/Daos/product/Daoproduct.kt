package com.Proyecto.coffeepalace.Data.Daos.product


import com.Proyecto.coffeepalace.Data.Model.producto
import com.Proyecto.coffeepalace.Data.Model.categoria

interface DaoProducto {
    suspend fun addProducto(producto: producto): Boolean
    suspend fun getCategoriasProducto(): List<categoria>
    suspend fun getProductos(): List<producto>
    suspend fun deleteProducto(id: Long?): Boolean
}