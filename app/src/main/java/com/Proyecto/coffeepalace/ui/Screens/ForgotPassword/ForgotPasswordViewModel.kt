package com.Proyecto.coffeepalace.ui.Screens.ForgotPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Repository.AuthRepository // <--- ¡NUEVA IMPORTACIÓN!
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage = _successMessage.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _errorMessage.value = null
        _successMessage.value = null
    }

    fun submitResetRequest() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _successMessage.value = null
            try {
                val response = authRepository.resetPassword(email.value)
                _successMessage.value = response.message ?: "Enlace de recuperación enviado si el correo está registrado."
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error al enviar solicitud de recuperación."
                println("Error en ForgotPasswordViewModel: ${e.message}")
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}