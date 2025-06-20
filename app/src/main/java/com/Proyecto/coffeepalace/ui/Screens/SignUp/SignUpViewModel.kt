package com.Proyecto.coffeepalace.ui.Screens.SignUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible.asStateFlow()

    private val _isConfirmPasswordVisible = MutableStateFlow(false)
    val isConfirmPasswordVisible: StateFlow<Boolean> = _isConfirmPasswordVisible.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        clearMessages()
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        clearMessages()
    }

    fun onConfirmPasswordChange(newValue: String) {
        _confirmPassword.value = newValue
        clearMessages()
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun toggleConfirmPasswordVisibility() {
        _isConfirmPasswordVisible.value = !_isConfirmPasswordVisible.value
    }

    private fun clearMessages() {
        _errorMessage.value = null
        _successMessage.value = null
    }

    fun onSignUpClick(): Boolean {
        val emailValue = _email.value.trim()
        val passwordValue = _password.value
        val confirmValue = _confirmPassword.value


        if (emailValue.isBlank()) {
            _errorMessage.value = "El correo no puede estar vacío"
            return false
        }

        if (passwordValue.length < 8) {
            _errorMessage.value = "La contraseña debe tener al menos 8 caracteres"
            return false
        }

        if (passwordValue != confirmValue) {
            _errorMessage.value = "Las contraseñas no coinciden"
            return false
        }

        _isLoading.value = true
        viewModelScope.launch {
            delay(2000)
            _isLoading.value = false
            _successMessage.value = "¡Registro exitoso!"
            delay(3000)
            _successMessage.value = null
        }
        return true
    }
}


