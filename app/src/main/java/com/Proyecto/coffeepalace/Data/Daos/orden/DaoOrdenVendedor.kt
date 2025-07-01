package com.Proyecto.coffeepalace.Data.Daos.orden

import com.Proyecto.coffeepalace.Data.Model.FacturaConEstado

interface DaoOrdenVendedor {
    suspend fun getAllOrdenVendedor(): List<FacturaConEstado>
}