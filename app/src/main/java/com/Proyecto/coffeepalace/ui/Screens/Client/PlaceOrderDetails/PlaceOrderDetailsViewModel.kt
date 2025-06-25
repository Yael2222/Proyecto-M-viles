package com.Proyecto.coffeepalace.ui.Screens.Client.PlaceOrderDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.shoppingCard.DaoShoppingCarImpl
import com.Proyecto.coffeepalace.Data.Model.Client.CarritoProductos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaceOrderDetailsViewModel : ViewModel() {
    private val dao = DaoShoppingCarImpl()

    private val _orderDetails = MutableStateFlow<CarritoProductos?>(null)
    val orderDetails = _orderDetails.asStateFlow()

    fun getOwnShoppingCarById(shoppingCarId: Long) {
        viewModelScope.launch {
            _orderDetails.value = dao.getOwnShoppingCarById(shoppingCarId)
        }
    }
}