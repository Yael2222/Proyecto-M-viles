package com.Proyecto.coffeepalace.ui.Screens.Seller.Stadistics

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class ProductStats(val name: String, val price: String, val unitsSold: Int)

class StadisticsViewModel : ViewModel() {
    val totalSales = mutableStateOf("$8,942")
    val totalOrders = mutableStateOf("426")

    val salesTrends = listOf(120, 150, 130, 170, 200, 180, 140) // ejemplo

    val bestSellingProducts = listOf(
        ProductStats("Iced Coffee", "$5.75", 100),
        ProductStats("Croissant", "$8.99", 100)
    )
}
