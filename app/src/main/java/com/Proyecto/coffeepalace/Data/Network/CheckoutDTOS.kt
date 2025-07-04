package com.Proyecto.coffeepalace.Data.Network

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderBody(val userId: Long)

@Serializable
data class CaptureBody(val userId: Long, val orderId: String)

@Serializable
data class OrderIdResponse(val orderId: String)