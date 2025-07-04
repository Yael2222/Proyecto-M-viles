package com.Proyecto.coffeepalace.ui.Screens.Client.CarDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.shoppingCard.DaoShoppingCarImpl
import com.Proyecto.coffeepalace.Data.Daos.user.DaoUsuarioImpl
import com.Proyecto.coffeepalace.Data.Model.Client.CarritoProductos
import com.Proyecto.coffeepalace.Data.Model.Client.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CarDetailsViewModel : ViewModel() {
    private val dao = DaoShoppingCarImpl()
    private val userDao = DaoUsuarioImpl()

    private val _shoppingcarProducts = MutableStateFlow<List<CarritoProductos>>(emptyList())
    val shoppingcarProducts = _shoppingcarProducts.asStateFlow()

    private val _userInformation = MutableStateFlow<Usuario?>(null)
    val userInformation = _userInformation.asStateFlow()

    private val _total = MutableStateFlow<Double>(0.0)
    val total = _total.asStateFlow()

    init {
        loadShoppingcarProducts(11)
        loadUserInformation(11)
    }

    fun loadShoppingcarProducts(userId: Long) {
        viewModelScope.launch {
            _shoppingcarProducts.value = dao.getAllOwnShoppingCar(userId)
        }
    }

    fun loadUserInformation(userId: Long) {
        viewModelScope.launch {
            _userInformation.value = userDao.getUserById(userId)
        }
    }

    fun getTotalFromShoppingCar() {
        var total = 0.0
        shoppingcarProducts.value.forEach {
            total += it.producto.precio
        }
        _total.value = total
    }
}