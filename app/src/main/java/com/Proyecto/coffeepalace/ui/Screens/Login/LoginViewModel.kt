package com.Proyecto.coffeepalace.ui.Screens.Login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.AuthResponse
import com.Proyecto.coffeepalace.Data.Repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.Proyecto.coffeepalace.utils.SessionManager // <--- ¡Importación ya existente!

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible = _isPasswordVisible.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

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

    fun setErrorMessage(message: String?) {
        _errorMessage.value = message
    }

    fun onLoginClick(onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = authRepository.signIn(email.value, password.value)
                if (response.user != null && response.session != null) {
                    println("Login exitoso para: ${response.user.email}")
                    // --- ¡CORRECCIÓN AQUÍ! Pasar userEmail y userName ---
                    sessionManager.saveSession(
                        accessToken = response.session.accessToken,
                        refreshToken = response.session.refreshToken,
                        userId = response.user.id,
                        userEmail = response.user.email,
                        // El nombre no se devuelve por defecto en signInWithPassword en userMetadata,
                        // a menos que tu backend lo añada explícitamente en la respuesta AuthResponse.
                        // Para esta llamada, lo dejamos como null si no está disponible.
                        userName = null
                    )
                    onLoginSuccess()
                } else {
                    _errorMessage.value = response.message ?: "Credenciales incorrectas o usuario no encontrado."
                }
            } catch (e: Exception) {
                if (e.message?.contains("Email not confirmed", ignoreCase = true) == true ||
                    e.message?.contains("email_not_confirmed", ignoreCase = true) == true) {
                    _errorMessage.value = "Tu correo electrónico no ha sido confirmado. Por favor, revisa tu bandeja de entrada y haz clic en el enlace de verificación."
                } else if (e.message?.contains("Invalid login credentials", ignoreCase = true) == true ||
                    e.message?.contains("AuthApiError", ignoreCase = true) == true) {
                    _errorMessage.value = "Correo electrónico o contraseña incorrectos."
                } else {
                    _errorMessage.value = e.message ?: "Error desconocido en el inicio de sesión."
                }
                println("Error en LoginViewModel: ${e.message}")
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getGoogleSignInIntent(): Intent? {
        return authRepository.getGoogleSignInIntent()
    }

    fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>, onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val idToken = authRepository.handleGoogleSignInResult(task)
                if (idToken != null) {
                    val response = authRepository.signInWithGoogleOnBackend(idToken)
                    if (response.user != null && response.session != null) {
                        println("Login Google exitoso para: ${response.user.email}")
                        // --- ¡CORRECCIÓN AQUÍ! Pasar userEmail y userName ---
                        sessionManager.saveSession(
                            accessToken = response.session.accessToken,
                            refreshToken = response.session.refreshToken,
                            userId = response.user.id,
                            userEmail = response.user.email,
                            // Asumiendo que response.user.userMetadata es Map<String, Any>
                            // y que contiene la clave "name".
                            userName = response.user.userMetadata?.get("name") as? String
                        )
                        onLoginSuccess()
                    } else {
                        _errorMessage.value = response.message ?: "Fallo la autenticación con Google en el backend."
                    }
                } else {
                    _errorMessage.value = "Fallo al obtener ID Token de Google."
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error en el inicio de sesión con Google."
                println("Error en LoginViewModel (Google Sign-In): ${e.message}")
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}