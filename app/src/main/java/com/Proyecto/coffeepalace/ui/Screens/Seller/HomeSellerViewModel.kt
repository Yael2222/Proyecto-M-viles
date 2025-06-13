package com.Proyecto.coffeepalace.ui.Screens.Seller


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Dummy.Order
import com.Proyecto.coffeepalace.Data.Dummy.HomeSellerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeSellerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeSellerState())
    val uiState: StateFlow<HomeSellerState> = _uiState

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            // Simulación de datos
            val orders = listOf(
                Order("Alejandra P.", 18.0, "Pendiente"),
                Order("Eduardo G.", 9.0, "En preparación"),
                Order("Eduardo G.", 13.0, "Entregado")
            )
            _uiState.value = HomeSellerState(
                salesToday = 200.0,
                ordersToday = 3,
                currentOrders = orders
            )
        }
    }
}
