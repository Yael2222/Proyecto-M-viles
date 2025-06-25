package com.Proyecto.coffeepalace.ui.Screens.Client.HomeFiltered

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.category.DaoCategoryImpl
import com.Proyecto.coffeepalace.Data.Daos.product.DaoProductoImpl
import com.Proyecto.coffeepalace.Data.Model.Client.CategoryAndProductsUIModel
import com.Proyecto.coffeepalace.Data.Model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeFilteredViewModel : ViewModel() {
    private val productDao = DaoProductoImpl()
    private val categoryDao = DaoCategoryImpl()

    private val _productsWithCategory = MutableStateFlow<CategoryAndProductsUIModel?>(null)
    val productsWithCategory = _productsWithCategory.asStateFlow()

    private val _allProducts = MutableStateFlow<List<Producto>>(emptyList())
    val allProducts = _allProducts.asStateFlow()

    init {
        loadAllProducts()
    }

    fun loadProducts(categoryId: Long) {
        viewModelScope.launch {
            val category = categoryDao.getCategoryById(categoryId)
            if (category != null) {
                val products = productDao.getProductsByCategory(category.id)
                _productsWithCategory.update {
                    CategoryAndProductsUIModel(category, products)
                }
            }
        }
    }

    fun loadAllProducts() {
        viewModelScope.launch {
            val products = productDao.getAllProducts()
            _allProducts.update { products }
        }
    }
}