package com.Proyecto.coffeepalace.Data.Daos.detalleDeFactura

import com.Proyecto.coffeepalace.Data.Model.detalleDeFactura

interface DaoDetalleDeFactura {
    suspend fun getAllDetalles(): List<detalleDeFactura>
}
