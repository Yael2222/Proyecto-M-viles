package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Daos.Busqueda.DaoProductoImpl
import com.Proyecto.coffeepalace.Data.Model.producto

class ProductRepository(
    private val daoProductoImpl: DaoProductoImpl
) {
    suspend fun getProductoById(idProducto: Int): producto? {
        return daoProductoImpl.obtenerProductoPorId(idProducto)
    }
}

