package com.Proyecto.coffeepalace.Data.Daos.producto

import com.Proyecto.coffeepalace.Data.Model.producto

interface DaoProducto {
    suspend fun getAllProductos(): List<producto>
}