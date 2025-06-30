package com.Proyecto.coffeepalace.ui.Screens.Customer.Forgot

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {

    // Email actual
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    // Mensaje de error
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Mensaje de éxito
    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    // Estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _errorMessage.value = null
        _successMessage.value = null
    }

    fun submitResetRequest() {
        val trimmedEmail = _email.value.trim()

        if (trimmedEmail.isEmpty()) {
            _errorMessage.value = "El campo de correo está vacío"
            _successMessage.value = null
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            _errorMessage.value = "Correo no válido"
            _successMessage.value = null
            return
        }

        // Simular envío con "loading"
        _isLoading.value = true

        viewModelScope.launch {
            // Simulacion tiempo de espera como si hiciera una petición real
            delay(2000)
            println("Email enviado a: $trimmedEmail")

            _isLoading.value = false
            _errorMessage.value = null
            _successMessage.value = "¡Correo de recuperación enviado exitosamente!"

            delay(2000)
            _isLoading.value = false
        }
    }
}

