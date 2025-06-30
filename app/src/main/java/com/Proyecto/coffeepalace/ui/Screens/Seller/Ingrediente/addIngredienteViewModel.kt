package com.Proyecto.coffeepalace.ui.Screens.Seller.Ingrediente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.ingrediente
import com.Proyecto.coffeepalace.Data.Repository.IngredienteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class addIngredienteViewModel(private val ingredienteRepository: IngredienteRepository) : ViewModel() {


    private val _ingredientes = MutableStateFlow<List<ingrediente>>(emptyList())
    val ingredientes = _ingredientes.asStateFlow()

    init {
        loadIngredientes()
    }

    fun loadIngredientes() {
        viewModelScope.launch {
            _ingredientes.value = ingredienteRepository.getAllIngredientes() // Llama al Repository
        }
    }

    fun addIngrediente(nombre: String) {
        viewModelScope.launch {
            if (ingredienteRepository.addIngrediente(nombre)) { // Llama al Repository
                loadIngredientes()
            }
        }
    }

    fun deleteIngrediente(id: Long) {
        viewModelScope.launch {
            if (ingredienteRepository.deleteIngrediente(id)) { // Llama al Repository
                loadIngredientes()
            }
        }
    }
}