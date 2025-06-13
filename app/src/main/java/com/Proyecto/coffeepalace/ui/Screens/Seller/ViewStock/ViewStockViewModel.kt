package com.Proyecto.coffeepalace.ui.Screens.Seller.ViewStock

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

data class StockItem(val name: String, var quantity: Int)

class ViewStockViewModel : ViewModel() {
    var stockItems = mutableStateListOf(
        StockItem("Café Arábica", 12),
        StockItem("Café Robusta", 8),
        StockItem("Almond Milk", 6),
        StockItem("Vasos 12oz", 350),
        StockItem("Vasos 16oz", 120)
    )

    var searchQuery = mutableStateOf("")
    var selectedItem = mutableStateOf<StockItem?>(null)

    fun updateStock(name: String, newQty: Int) {
        val index = stockItems.indexOfFirst { it.name == name }
        if (index != -1) {
            stockItems[index] = stockItems[index].copy(quantity = newQty)
        }
    }

    fun filterItems(): List<StockItem> {
        return stockItems.filter {
            it.name.contains(searchQuery.value, ignoreCase = true)
        }
    }
}
