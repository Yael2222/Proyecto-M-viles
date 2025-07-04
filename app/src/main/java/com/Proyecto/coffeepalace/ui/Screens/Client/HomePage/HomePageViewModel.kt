package com.Proyecto.coffeepalace.ui.Screens.Client.HomePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.category.DaoCategoryImpl
import com.Proyecto.coffeepalace.Data.Daos.discount.DaoAnuncioImpl
import com.Proyecto.coffeepalace.Data.Daos.product.DaoProductoImpl
import com.Proyecto.coffeepalace.Data.Model.Anuncio
import com.Proyecto.coffeepalace.Data.Model.Client.CategoryAndProductsUIModel
import com.Proyecto.coffeepalace.Data.Model.Categoria
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomePageViewModel : ViewModel() {
    private val dao = DaoProductoImpl()
    private val categoryDao = DaoCategoryImpl()
    private val anuncioDao = DaoAnuncioImpl()

    private val _productsWithCategories =
        MutableStateFlow<List<CategoryAndProductsUIModel>>(emptyList())
    val productsWithCategories = _productsWithCategories.asStateFlow()

    private val _announcements = MutableStateFlow<List<Anuncio>>(emptyList())
    val announcements = _announcements.asStateFlow()

    private val _categories = MutableStateFlow<List<Categoria>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        initialLoad()
    }

    fun initialLoad() {
        loadCategories()
        loadAnnouncements()
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            val categories = categoryDao.getAllCategories()
            val products = dao.getAllProducts()

            val categoryAndProducts = categories.map { category ->
                val productsFromCategory =
                    products.filter { product -> product.categoria == category.id }
                CategoryAndProductsUIModel(category, productsFromCategory)
            }

            _productsWithCategories.value = categoryAndProducts
        }
    }

    fun loadAnnouncements() {
        viewModelScope.launch {
            _announcements.value = anuncioDao.getAllAnuncios()
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = categoryDao.getAllCategories()
        }
    }
}