package com.Proyecto.coffeepalace.ui.Screens.Seller.AddProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.product.DaoProductImpl
import com.Proyecto.coffeepalace.Data.Model.categoria
import com.Proyecto.coffeepalace.Data.Model.producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddProductViewModel : ViewModel() {

    private val dao = DaoProductImpl()

    val name = MutableStateFlow("")
    val price = MutableStateFlow("")
    val description = MutableStateFlow("")
    val imageUri = MutableStateFlow<String?>(null)
    val selectedCategoryId = MutableStateFlow<Long?>(null)

    private val _categorias = MutableStateFlow<List<categoria>>(emptyList())
    val categorias = _categorias.asStateFlow()

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess = _saveSuccess.asStateFlow()

    init {
        loadCategorias()
    }

    fun loadCategorias() {
        viewModelScope.launch {
            val result = dao.getCategoriasProducto()
            println("CATEGORIAS CARGADAS: $result") // DEBUG
            _categorias.value = result
        }
    }

    fun saveProduct() {
        viewModelScope.launch {
            val priceValue = price.value.toDoubleOrNull()
            val categoryId = selectedCategoryId.value

            if (name.value.isBlank() || priceValue == null || description.value.isBlank() || categoryId == null) {
                println("Invalid input: Please fill all fields correctly.")
                return@launch
            }

            val producto = producto(
                nombre = name.value,
                descripcion = description.value,
                imagen = imageUri.value ?: "",
                precio = priceValue,
                categoria = categoryId
            )

            _saveSuccess.value = dao.addProducto(producto)
        }
    }

    fun resetForm() {
        name.value = ""
        price.value = ""
        description.value = ""
        selectedCategoryId.value = null
        imageUri.value = null
        _saveSuccess.value = false
    }
}
