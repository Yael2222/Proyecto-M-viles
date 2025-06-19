package com.Proyecto.coffeepalace.ui.Screens.Forgot

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class ForgotPasswordViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    var successMessage = mutableStateOf<String?>(null)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
        errorMessage.value = null
        successMessage.value = null
    }

    fun submitResetRequest() {
        val trimmedEmail = email.trim()

        if (trimmedEmail.isEmpty()) {
            errorMessage.value = "El campo de correo está vacío"
            successMessage.value = null
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            errorMessage.value = "Correo no válido"
            successMessage.value = null
            return
        }
        //Simulando evío exitoso
        //lógica para enviar el correo de recuperación
        println("Email enviado a: $trimmedEmail")
        errorMessage.value = null
        successMessage.value = "¡Correo de recuperación enviado exitosamente!"
    }
}
