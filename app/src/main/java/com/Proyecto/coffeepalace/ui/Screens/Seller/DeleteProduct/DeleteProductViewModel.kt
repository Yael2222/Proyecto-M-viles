// Archivo: com/Proyecto/coffeepalace/ui/Screens/Seller/Product/DeleteProductViewModel.kt
package com.Proyecto.coffeepalace.ui.Screens.Seller.Product

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.product.DaoProductImpl
import com.Proyecto.coffeepalace.Data.Model.producto
import com.Proyecto.coffeepalace.Data.Model.categoria // Importa la data class categoria
import kotlinx.coroutines.launch

class DeleteProductViewModel : ViewModel() {
    private val dao = DaoProductImpl()

    var productos by mutableStateOf<List<producto>>(emptyList())
        private set

    var categorias by mutableStateOf<List<categoria>>(emptyList()) // Nuevo estado para las categorías
        private set

    var mensaje by mutableStateOf("")
        private set

    init {
        loadData() // Llama a una función que carga tanto productos como categorías
    }

    private fun loadData() {
        viewModelScope.launch {
            // Cargar categorías primero
            try {
                categorias = dao.getCategoriasProducto()
            } catch (e: Exception) {
                mensaje = "Error al cargar categorías: ${e.message}"
                println("Error al cargar categorías: ${e.message}")
            }

            // Luego cargar productos
            try {
                productos = dao.getProductos()
            } catch (e: Exception) {
                mensaje = "Error al cargar productos: ${e.message}"
                println("Error al cargar productos: ${e.message}")
            }
        }
    }

    fun deleteProduct(id: Long) {
        viewModelScope.launch {
            val deleted = dao.deleteProducto(id)
            if (deleted) {
                mensaje = "Producto eliminado con éxito"
                loadData() // Recargar ambas listas para asegurar consistencia
            } else {
                mensaje = "Error al eliminar el producto"
            }
        }
    }
}