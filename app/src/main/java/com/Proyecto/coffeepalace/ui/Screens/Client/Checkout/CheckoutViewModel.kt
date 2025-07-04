package com.Proyecto.coffeepalace.ui.Screens.Client.Checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.shoppingCard.DaoShoppingCarImpl
import com.Proyecto.coffeepalace.Data.Model.Client.CheckoutTotal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckoutViewModel : ViewModel() {

    companion object {
        const val Shipping_P = 0.1
    }

    private val _total = MutableStateFlow<CheckoutTotal>(CheckoutTotal())
    val total = _total.asStateFlow()

    private val dao = DaoShoppingCarImpl()

    init {
        getTotal(11)
    }

    fun getTotal(userId: Long) {
        viewModelScope.launch {
            val products = dao.getAllOwnShoppingCar(userId)
            var total = 0.0
            products.forEach {
                total += it.producto.precio
            }
            val totalModel = CheckoutTotal(
                subTotal = total,
                shipping = total * Shipping_P,
                total = total + total * Shipping_P
            )
            _total.value = totalModel

        }
    }
}