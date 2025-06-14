package com.Proyecto.coffeepalace.ui.Screens.Login

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isPasswordVisible by mutableStateOf(false)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onTogglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun onLoginClick() {
        // conectar con AuthRepository para validar usuario
        println("Logging in with: $email / $password")
    }
}
