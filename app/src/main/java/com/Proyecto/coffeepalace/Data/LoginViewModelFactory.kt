package com.Proyecto.coffeepalace.Data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.Proyecto.coffeepalace.Data.UserPreferences
import com.Proyecto.coffeepalace.ui.Screens.Login.LoginViewModel

class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val userPreferences = UserPreferences(context)
        return LoginViewModel(userPreferences) as T
    }
}
