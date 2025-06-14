package com.Proyecto.coffeepalace.ui.Screens.Forgot

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class ForgotPasswordViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun submitResetRequest() {
        //lógica para enviar el correo de recuperación
        println("Email enviado a: $email")
    }
}
