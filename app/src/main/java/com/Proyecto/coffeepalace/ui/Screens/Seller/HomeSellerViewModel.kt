package com.Proyecto.coffeepalace.ui.Screens.Seller.HomeSeller

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
            // Static data simulation for now, replicating the data from your image.
            val orders = listOf(
                Order("Alejandra P.", 18.00, "Pendiente"),
                Order("Eduardo G.", 9.00, "En preparación"),
                Order("Eduardo G.", 13.00, "Entregado") // Matches the image data
            )
            _uiState.value = HomeSellerState(
                salesToday = 200.00,
                ordersToday = 3,
                currentOrders = orders
            )
            println("HomeSellerViewModel: Initial data loaded. Sales: ${_uiState.value.salesToday}, Orders: ${_uiState.value.ordersToday}")
        }
    }


}