package com.Proyecto.coffeepalace.Data.Repository

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.Proyecto.coffeepalace.Data.Daos.order.DaoOrder
import com.Proyecto.coffeepalace.Data.Model.CartItem
import com.Proyecto.coffeepalace.Data.Model.CreateOrderResponse
import com.Proyecto.coffeepalace.Data.Model.OrdenWithDetails
import com.Proyecto.coffeepalace.Data.Model.orden_vendedor
import com.Proyecto.coffeepalace.Data.Network.ApiService
import com.Proyecto.coffeepalace.Data.Network.UpdateOrderStatusRequest
// --- CAMBIO AQUÍ: Importa CreateOrderRequest y OrderProductDetail directamente
import com.Proyecto.coffeepalace.Data.Network.CreateOrderRequest
import com.Proyecto.coffeepalace.Data.Network.OrderProductDetail

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

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun createOrder(request: CreateOrderRequest): CreateOrderResponse? {
        return try {
            apiService.createOrder(request)
        } catch (e: HttpException) {
            null
        } catch (e: Exception) {
            Log.e("OrderRepository", "Error creating order: ${e.message}", e)
            null
        }
    }

    suspend fun getUserOrders(userId: String): List<OrdenWithDetails> { // <--- Ya esperaba OrdenWithDetails
        return try {
            apiService.getUserOrders(userId) // Ahora ApiService devuelve el tipo correcto
        } catch (e: Exception) {
            println("Error al obtener las órdenes del usuario: ${e.localizedMessage}")
            e.printStackTrace()
            emptyList()
        }
    }
}