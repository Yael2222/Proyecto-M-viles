package com.Proyecto.coffeepalace.ui.Screens.SignUp

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var isPasswordVisible by mutableStateOf(false)
        private set

    var isConfirmPasswordVisible by mutableStateOf(false)
        private set

    fun onEmailChange(newValue: String) {
        email = newValue
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
    }

    fun onConfirmPasswordChange(newValue: String) {
        confirmPassword = newValue
    }

    fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun toggleConfirmPasswordVisibility() {
        isConfirmPasswordVisible = !isConfirmPasswordVisible
    }

    fun onSignUpClick() {
        //lógica para registrar usuario (Room)
    }
}
