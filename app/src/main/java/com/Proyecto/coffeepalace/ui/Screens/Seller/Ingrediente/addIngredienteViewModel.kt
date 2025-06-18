package com.Proyecto.coffeepalace.ui.Screens.Seller.Ingrediente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.ingrediente.DaoIngredienteImpl
import com.Proyecto.coffeepalace.Data.Model.ingrediente
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class addIngredienteViewModel : ViewModel() {

    private val dao = DaoIngredienteImpl()

    private val _ingredientes = MutableStateFlow<List<ingrediente>>(emptyList())
    val ingredientes = _ingredientes.asStateFlow()

    init {
        loadIngredientes()
    }

    fun loadIngredientes() {
        viewModelScope.launch {
            _ingredientes.value = dao.getAllIngredientes()
        }
    }

    fun addIngrediente(nombre: String) {
        viewModelScope.launch {
            if (dao.addIngrediente(nombre)) {
                loadIngredientes()
            }
        }
    }

    fun deleteIngrediente(id: Long) {
        viewModelScope.launch {
            if (dao.deleteIngrediente(id)) {
                loadIngredientes()
            }
        }
    }
}
