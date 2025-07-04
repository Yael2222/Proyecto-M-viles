package com.Proyecto.coffeepalace.ui.Screens.SignUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible = _isPasswordVisible.asStateFlow()

    private val _isConfirmPasswordVisible = MutableStateFlow(false)
    val isConfirmPasswordVisible = _isConfirmPasswordVisible.asStateFlow()

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

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _errorMessage.value = null
        _successMessage.value = null
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
        _errorMessage.value = null
        _successMessage.value = null
    }

    fun onNameChange(newName: String) {
        _name.value = newName
        _errorMessage.value = null
        _successMessage.value = null
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun toggleConfirmPasswordVisibility() {
        _isConfirmPasswordVisible.value = !_isConfirmPasswordVisible.value
    }

    fun onSignUpClick() {
        _errorMessage.value = null
        _successMessage.value = null

        if (email.value.isBlank() || password.value.isBlank() || confirmPassword.value.isBlank() || name.value.isBlank()) {
            _errorMessage.value = "Todos los campos (email, contraseña, nombre) son requeridos."
            return
        }

        // --- ¡NUEVA VALIDACIÓN PARA EL FORMATO DE EMAIL! ---
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            _errorMessage.value = "Formato de correo electrónico inválido."
            return
        }

        if (password.value != confirmPassword.value) {
            _errorMessage.value = "Las contraseñas no coinciden."
            return
        }

        if (password.value.length < 6) {
            _errorMessage.value = "La contraseña debe tener al menos 6 caracteres."
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = authRepository.signUp(email.value, password.value, name.value)
                if (response.user != null) {
                    _successMessage.value = "Registro exitoso. ¡Revisa tu correo para confirmar!"
                } else if (response.message != null) {
                    _errorMessage.value = response.message
                } else {
                    _errorMessage.value = "Fallo en el registro."
                }
            } catch (e: Exception) {
                if (e.message?.contains("AuthWeakPasswordError", ignoreCase = true) == true) {
                    _errorMessage.value = "La contraseña debe tener al menos 6 caracteres."
                } else if (e.message?.contains("User already registered", ignoreCase = true) == true ||
                    e.message?.contains("User already exists", ignoreCase = true) == true) {
                    _errorMessage.value = "El correo electrónico ya está registrado."
                } else if (e.message?.contains("Email address", ignoreCase = true) == true && e.message?.contains("is invalid", ignoreCase = true) == true) {
                    _errorMessage.value = "El formato del correo electrónico es inválido. Por favor, verifica." // Mensaje más específico
                } else {
                    _errorMessage.value = e.message ?: "Error desconocido en el registro."
                }
                println("Error en SignUpViewModel: ${e.message}")
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}