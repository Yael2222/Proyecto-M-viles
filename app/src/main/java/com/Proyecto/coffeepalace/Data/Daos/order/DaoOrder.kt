package com.Proyecto.coffeepalace.Data.Daos.order

import com.Proyecto.coffeepalace.Data.Model.OrdenWithDetails
import com.Proyecto.coffeepalace.Data.Model.orden_vendedor

interface DaoOrder {
    suspend fun getAllOrdersWithDetails(): List<OrdenWithDetails>
    suspend fun updateOrderStatus(orderId: Long, newEstado: String): orden_vendedor?
}