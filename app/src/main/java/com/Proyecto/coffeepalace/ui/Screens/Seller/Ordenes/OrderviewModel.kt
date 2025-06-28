package com.Proyecto.coffeepalace.ui.Screens.Seller.Ordenes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.OrdenWithDetails
import com.Proyecto.coffeepalace.Data.Repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderViewModel(private val orderRepository: OrderRepository) : ViewModel() {

    private val _orders = MutableStateFlow<List<OrdenWithDetails>>(emptyList())
    val orders: StateFlow<List<OrdenWithDetails>> = _orders.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchOrders()
    }

    fun fetchOrders() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _orders.value = orderRepository.getAllOrdersWithDetails()
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar las órdenes: ${e.message}"
                println("Error en OrderViewModel (fetchOrders): ${e.message}")
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateOrderStatus(orderId: Long, newEstado: String) {
        viewModelScope.launch {
            _isLoading.value = true // Opcional, puedes tener un isLoading solo para updates
            _errorMessage.value = null
            try {
                val updatedOrder = orderRepository.updateOrderStatus(orderId, newEstado)
                if (updatedOrder != null) {
                    _errorMessage.value = "Estado de orden ${updatedOrder.id} actualizado a ${updatedOrder.estado}"
                    fetchOrders() // Recargar la lista para reflejar el cambio
                } else {
                    _errorMessage.value = "Fallo al actualizar el estado de la orden."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error al actualizar estado de orden: ${e.message}"
                println("Error en OrderViewModel (updateOrderStatus): ${e.message}")
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}