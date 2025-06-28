package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Daos.order.DaoOrder // Tu interfaz DaoOrder
import com.Proyecto.coffeepalace.Data.Model.OrdenWithDetails
import com.Proyecto.coffeepalace.Data.Model.orden_vendedor
import com.Proyecto.coffeepalace.Data.Network.ApiService
import com.Proyecto.coffeepalace.Data.Network.UpdateOrderStatusRequest

class OrderRepository(private val apiService: ApiService) : DaoOrder {

    override suspend fun getAllOrdersWithDetails(): List<OrdenWithDetails> {
        return try {
            apiService.getAllOrdersWithDetails()
        } catch (e: Exception) {
            println("Error fetching orders with details from backend: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun updateOrderStatus(orderId: Long, newEstado: String): orden_vendedor? {
        return try {
            val requestBody = UpdateOrderStatusRequest(estado = newEstado)
            apiService.updateOrderStatus(orderId, requestBody)
        } catch (e: Exception) {
            println("Error updating order status from backend: ${e.message}")
            e.printStackTrace()
            null
        }
    }
}