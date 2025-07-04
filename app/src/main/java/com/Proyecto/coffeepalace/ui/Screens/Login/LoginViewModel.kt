package com.Proyecto.coffeepalace.ui.Screens.Login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.AuthResponse
import com.Proyecto.coffeepalace.Data.Repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.android.gms.tasks.Task

class LoginViewModel(
    private val authRepository: AuthRepository
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

    // onLoginClick MODIFICADO para obtener el rol de la tabla 'usuario' a través del backend
    fun onLoginClick(onLoginSuccess: (String?) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val authResponse = authRepository.signIn(email.value, password.value)
                if (authResponse.user != null && authResponse.session != null) {
                    Log.d("LoginViewModel", "Login exitoso para: ${authResponse.user.email}")
                    val userEmail = authResponse.user.email // Obtener el correo del usuario

                    // Asegurarse de que el correo no sea nulo antes de pasarlo al backend
                    if (userEmail != null) {
                        // Obtener el perfil completo del usuario desde TU BACKEND usando el correo
                        val customUsuario = authRepository.getUsuarioProfileFromBackend(userEmail) // <--- CAMBIO CLAVE AQUÍ: pasar userEmail

                        if (customUsuario != null) {
                            Log.d("LoginViewModel", "Rol obtenido de la tabla usuario: ${customUsuario.rol}")
                            onLoginSuccess(customUsuario.rol) // Pasa el rol de tu tabla 'usuario'
                        } else {
                            _errorMessage.value = "No se pudo encontrar el perfil de usuario. Inténtalo de nuevo."
                            Log.e("LoginViewModel", "Perfil de usuario no encontrado para email: $userEmail")
                            onLoginSuccess(null)
                        }
                    } else {
                        _errorMessage.value = "Error: El correo del usuario es nulo después del inicio de sesión."
                        Log.e("LoginViewModel", "El correo de usuario es nulo en authResponse.user.email")
                        onLoginSuccess(null)
                    }
                } else {
                    _errorMessage.value = authResponse.message ?: "Credenciales incorrectas o usuario no encontrado."
                    Log.e("LoginViewModel", "Fallo de autenticación: ${authResponse.message}")
                    onLoginSuccess(null)
                }
            } catch (e: Exception) {
                val errorMsg = when {
                    e.message?.contains("Email not confirmed", ignoreCase = true) == true ||
                            e.message?.contains("email_not_confirmed", ignoreCase = true) == true ->
                        "Tu correo electrónico no ha sido confirmado. Por favor, revisa tu bandeja de entrada y haz clic en el enlace de verificación."
                    e.message?.contains("Invalid login credentials", ignoreCase = true) == true ||
                            e.message?.contains("AuthApiError", ignoreCase = true) == true ->
                        "Correo electrónico o contraseña incorrectos."
                    else ->
                        e.message ?: "Error desconocido en el inicio de sesión."
                }
                _errorMessage.value = errorMsg
                Log.e("LoginViewModel", "Error en onLoginClick: $errorMsg", e)
                onLoginSuccess(null)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getGoogleSignInIntent(): Intent? {
        return authRepository.getGoogleSignInIntent()
    }

    fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>, onLoginSuccess: (String?) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val idToken = authRepository.handleGoogleSignInResult(task)
                if (idToken != null) {
                    val authResponse = authRepository.signInWithGoogleOnBackend(idToken)
                    if (authResponse.user != null && authResponse.session != null) {
                        Log.d("LoginViewModel", "Login Google exitoso para: ${authResponse.user.email}")
                        val userEmail = authResponse.user.email // Obtener el correo del usuario

                        // Asegurarse de que el correo no sea nulo antes de pasarlo al backend
                        if (userEmail != null) {
                            // Obtener el perfil completo del usuario desde TU BACKEND usando el correo
                            val customUsuario = authRepository.getUsuarioProfileFromBackend(userEmail) // <--- CAMBIO CLAVE AQUÍ: pasar userEmail

                            if (customUsuario != null) {
                                Log.d("LoginViewModel", "Rol obtenido de la tabla usuario (Google): ${customUsuario.rol}")
                                onLoginSuccess(customUsuario.rol) // Pasa el rol de tu tabla 'usuario'
                            } else {
                                _errorMessage.value = "No se pudo encontrar el perfil de usuario después del inicio de sesión con Google. Inténtalo de nuevo."
                                Log.e("LoginViewModel", "Perfil de usuario no encontrado para email (Google): $userEmail")
                                onLoginSuccess(null)
                            }
                        } else {
                            _errorMessage.value = "Error: El correo del usuario es nulo después del inicio de sesión con Google."
                            Log.e("LoginViewModel", "El correo de usuario es nulo en authResponse.user.email para Google")
                            onLoginSuccess(null)
                        }
                    } else {
                        _errorMessage.value = authResponse.message ?: "Fallo la autenticación con Google en el backend."
                        Log.e("LoginViewModel", "Fallo de autenticación con Google: ${authResponse.message}")
                        onLoginSuccess(null)
                    }
                } else {
                    _errorMessage.value = "Fallo al obtener ID Token de Google."
                    Log.e("LoginViewModel", "ID Token de Google nulo.")
                    onLoginSuccess(null)
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error en el inicio de sesión con Google."
                Log.e("LoginViewModel", "Error en handleGoogleSignInResult (Google Sign-In): ${e.message}", e)
                onLoginSuccess(null)
            } finally {
                _isLoading.value = false
            }
        }
    }
}