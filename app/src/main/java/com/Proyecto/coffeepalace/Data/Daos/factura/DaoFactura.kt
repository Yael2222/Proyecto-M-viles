package com.Proyecto.coffeepalace.Data.Daos.factura

import com.Proyecto.coffeepalace.Data.Model.factura

interface DaoFactura {
    suspend fun getAllFacturas(): List<factura>
}