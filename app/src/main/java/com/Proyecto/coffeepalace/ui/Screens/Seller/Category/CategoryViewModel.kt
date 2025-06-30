package com.Proyecto.coffeepalace.ui.Screens.Seller.Category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.categoria
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val dao = DaoCategoryImpl()

    private val _categories = MutableStateFlow<List<categoria>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = dao.getAllCategories()
        }
    }

    fun addCategory(nombre: String) {
        viewModelScope.launch {
            if (dao.addCategory(nombre)) {
                loadCategories()
            }
        }
    }

    fun deleteCategory(id: Long) {
        viewModelScope.launch {
            if (dao.deleteCategory(id)) {
                loadCategories()
            }
        }
    }
}
