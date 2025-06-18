package com.Proyecto.coffeepalace.ui.Screens.Seller.AddProduct

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AddProductViewModel : ViewModel() {

    var name = mutableStateOf("")
    var price = mutableStateOf("")
    var category = mutableStateOf("")
    var description = mutableStateOf("")
    var imageUri = mutableStateOf<String?>(null)

    fun saveProduct() {
        // Aquí podrías validar y enviar a la BD o repo
        println("Producto guardado: ${name.value}, ${price.value}")
    }

    fun resetFields() {
        name.value = ""
        price.value = ""
        category.value = ""
        description.value = ""
        imageUri.value = null
    }
}
