package com.Proyecto.coffeepalace.ui.Screens.Seller.Category

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class CategoryViewModel : ViewModel() {
    var categories = mutableStateListOf("Category 1", "Category 2", "Category 3")
        private set

    fun addCategory(name: String) {
        categories.add(name)
    }

    fun editCategory(index: Int, newName: String) {
        if (index in categories.indices) {
            categories[index] = newName
        }
    }
}
