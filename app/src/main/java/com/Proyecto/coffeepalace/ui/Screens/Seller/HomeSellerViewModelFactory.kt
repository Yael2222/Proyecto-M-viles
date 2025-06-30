package com.Proyecto.coffeepalace.ui.Screens.Seller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.Proyecto.coffeepalace.di.AppContainer
import com.Proyecto.coffeepalace.ui.Screens.Seller.HomeSeller.HomeSellerViewModel

class HomeSellerViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeSellerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeSellerViewModel(AppContainer.authRepository) as T // <--- Pasa el AuthRepository
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}