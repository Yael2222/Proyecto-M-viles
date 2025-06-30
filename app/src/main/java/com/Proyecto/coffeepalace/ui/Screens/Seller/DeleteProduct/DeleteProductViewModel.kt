package com.Proyecto.coffeepalace.ui.Screens.Seller.Product

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.categoria
import com.Proyecto.coffeepalace.Data.Model.producto
import com.Proyecto.coffeepalace.Data.Repository.ProductRepository
import kotlinx.coroutines.launch

class DeleteProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    var productos by mutableStateOf<List<producto>>(emptyList())
        private set

    var categorias by mutableStateOf<List<categoria>>(emptyList())
        private set

    var mensaje by mutableStateOf("")
        private set

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                categorias = productRepository.getCategoriasProducto()
            } catch (e: Exception) {
                mensaje = "Error al cargar categorías: ${e.message}"
                println(mensaje)
            }

            try {
                productos = productRepository.getProductos()
            } catch (e: Exception) {
                mensaje = "Error al cargar productos: ${e.message}"
                println(mensaje)
            }
        }
    }

    fun deleteProduct(id: Long) {
        viewModelScope.launch {
            val deleted = productRepository.deleteProducto(id)
            if (deleted) {
                mensaje = "Producto eliminado con éxito"
                loadData()
            } else {
                mensaje = "Error al eliminar el producto"
            }
        }
    }
}
