package com.Proyecto.coffeepalace.Data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.Proyecto.coffeepalace.di.AppContainer
import com.Proyecto.coffeepalace.ui.Screens.Login.LoginViewModel

class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // LoginViewModel ahora solo necesita authRepository
            return LoginViewModel(AppContainer.authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}