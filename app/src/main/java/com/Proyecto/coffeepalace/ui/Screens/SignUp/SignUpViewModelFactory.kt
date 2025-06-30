package com.Proyecto.coffeepalace.ui.Screens.SignUp


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.Proyecto.coffeepalace.di.AppContainer
import com.Proyecto.coffeepalace.ui.Screens.SignUp.SignUpViewModel

class SignUpViewModelFactory : ViewModelProvider.Factory { // No necesita Context si AuthRepo ya está en AppContainer
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignUpViewModel(AppContainer.authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}