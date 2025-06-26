package com.Proyecto.coffeepalace.Data.Dummy

class HomeSellerState (
    val salesToday : Double= 0.0,
    val ordersToday: Int = 0,
    val currentOrders: List<Order> = emptyList()
)