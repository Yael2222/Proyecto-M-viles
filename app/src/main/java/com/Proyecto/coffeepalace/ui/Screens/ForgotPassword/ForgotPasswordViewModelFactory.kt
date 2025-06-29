package com.Proyecto.coffeepalace.ui.Screens.ForgotPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.Proyecto.coffeepalace.di.AppContainer
import com.Proyecto.coffeepalace.ui.Screens.ForgotPassword.ForgotPasswordViewModel

class ForgotPasswordViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ForgotPasswordViewModel(AppContainer.authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}