package com.Proyecto.coffeepalace.ui.Screens.Client.ProductDetail // Asegúrate de que este sea el paquete correcto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.Proyecto.coffeepalace.Data.Repository.AuthRepository
import com.Proyecto.coffeepalace.Data.Repository.ComentariosRepository
import com.Proyecto.coffeepalace.Data.Repository.ProductRepository
import com.Proyecto.coffeepalace.Data.Repository.ShoppingCartRepository
import com.Proyecto.coffeepalace.di.AppContainer

class ProductDetailViewModelFactory(
    private val appContainer: AppContainer
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            val savedStateHandle = extras.createSavedStateHandle()

            return ProductDetailViewModel(
                productosRepository = appContainer.productRepository,
                comentariosRepository = appContainer.comentariosRepository,
                authRepository = appContainer.authRepository,
                shoppingCartRepository = appContainer.shoppingCartRepository,
                savedStateHandle = savedStateHandle
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}