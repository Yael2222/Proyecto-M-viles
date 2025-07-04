package com.Proyecto.coffeepalace.ui.Screens.Seller.Category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.Categoria
import com.Proyecto.coffeepalace.Data.Repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {

    private val _categories = MutableStateFlow<List<Categoria>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = categoryRepository.getAllCategories()
        }
    }

    fun addCategory(nombre: String) {
        viewModelScope.launch {
            if (categoryRepository.addCategory(nombre)) {
                loadCategories()
            }
        }
    }

    fun deleteCategory(id: Long) {
        viewModelScope.launch {
            if (categoryRepository.deleteCategory(id)) {
                loadCategories()
            }
        }
    }
}