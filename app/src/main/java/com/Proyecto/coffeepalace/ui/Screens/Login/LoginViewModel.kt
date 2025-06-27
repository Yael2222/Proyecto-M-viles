package com.Proyecto.coffeepalace.ui.Screens.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.UserPreferences
import com.Proyecto.coffeepalace.Data.Remote.RetrofitClient
import com.Proyecto.coffeepalace.Data.Remote.auth.Login.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _errorMessage.value = null
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _errorMessage.value = null
    }

    fun onTogglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun onLoginClick(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = RetrofitClient.authService.login(
                    LoginRequest(email = _email.value, password = _password.value)
                )

                // Guarda el token en DataStore
                userPreferences.saveToken(response.token)

                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = "Login fallido: ${e.localizedMessage ?: "Error desconocido"}"
            }

            _isLoading.value = false
        }
    }
}
