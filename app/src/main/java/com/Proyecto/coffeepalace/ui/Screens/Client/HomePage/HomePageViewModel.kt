package com.Proyecto.coffeepalace.ui.Screens.Client.HomePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.categoria
import com.Proyecto.coffeepalace.Data.Model.producto
import com.Proyecto.coffeepalace.Data.Repository.CategoryRepository
import com.Proyecto.coffeepalace.Data.Repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<categoria>>(emptyList())
    val categories: StateFlow<List<categoria>> = _categories

    private val _productsByCategory = MutableStateFlow<Map<Long, List<producto>>>(emptyMap())
    val productsByCategory: StateFlow<Map<Long, List<producto>>> = _productsByCategory

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                // Fetch categories
                val fetchedCategories = categoryRepository.getAllCategories()
                _categories.value = fetchedCategories

                // Fetch all products
                val allProducts = productRepository.getProductos()

                // Group products by category ID
                val groupedProducts = allProducts.groupBy { it.categoria }
                _productsByCategory.value = groupedProducts

            } catch (e: Exception) {
                _errorMessage.value = "Error loading data: ${e.message}"
                println("Error fetching home data: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshData() {
        fetchHomeData()
    }
}

// ViewModel Factory for HomeViewModel
class HomeViewModelFactory(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(productRepository, categoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}