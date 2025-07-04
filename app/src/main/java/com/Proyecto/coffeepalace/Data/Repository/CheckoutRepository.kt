package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Network.ApiService
import com.Proyecto.coffeepalace.Data.Network.CaptureBody
import com.Proyecto.coffeepalace.Data.Network.CreateOrderBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckoutRepository(private val apiService: ApiService) {
    suspend fun createOrder(userId: Long): String =
        apiService.createOrder(CreateOrderBody(userId)).orderId

    suspend fun captureOrder(userId: Long, orderId: String) {
        try {
            withContext(Dispatchers.IO) {
                apiService.captureOrder(CaptureBody(userId, orderId))
            }
        } catch (e: Exception) {
            println("Network error: ${e.stackTraceToString()}")
            throw e
        }
    }
}