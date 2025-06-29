package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Model.AuthResponse
import com.Proyecto.coffeepalace.Data.Model.SessionResponse
import com.Proyecto.coffeepalace.Data.Model.UserResponse
import com.Proyecto.coffeepalace.Data.Network.ApiService
import com.Proyecto.coffeepalace.Data.Network.AuthRequest
import com.Proyecto.coffeepalace.Data.Network.ResetPasswordRequest
import com.Proyecto.coffeepalace.Data.Network.SignUpRequest
import com.Proyecto.coffeepalace.Data.Network.GoogleSignInTokenRequest
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.Proyecto.coffeepalace.utils.SessionManager // <--- ¡NUEVA IMPORTACIÓN!

class AuthRepository(
    private val apiService: ApiService,
    private val googleSignInClient: GoogleSignInClient?,
    private val sessionManager: SessionManager // <--- ¡NUEVO PARÁMETRO EN EL CONSTRUCTOR!
) {
    suspend fun signUp(email: String, password: String, name: String): AuthResponse {
        return apiService.signUp(SignUpRequest(email, password, name))
    }

    suspend fun signIn(email: String, password: String): AuthResponse {
        return apiService.signIn(AuthRequest(email, password))
    }

    suspend fun resetPassword(email: String): AuthResponse {
        return apiService.resetPassword(ResetPasswordRequest(email))
    }

    fun getGoogleSignInIntent() = googleSignInClient?.signInIntent

    suspend fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>): String? {
        return suspendCoroutine { continuation ->
            task.addOnCompleteListener { completedTask ->
                if (completedTask.isSuccessful) {
                    val account = completedTask.result
                    continuation.resume(account?.idToken)
                } else {
                    continuation.resume(null)
                }
            }
        }
    }

    suspend fun signInWithGoogleOnBackend(idToken: String): AuthResponse {
        return apiService.signInWithGoogleToken(GoogleSignInTokenRequest(idToken))
    }

    suspend fun signOut(): Boolean {
        return try {
            // 1. Opcional: Llamar al endpoint de logout de tu backend (si lo creaste)
            // Esto es buena práctica para invalidar la sesión en el servidor (refresh token).
            // Si el usuario inició sesión con email/password, esto invalida su sesión de Supabase.
            // Si inició con Google, Supabase también gestiona la sesión.
            val backendResponse = apiService.signOutBackend() // Asegúrate de que este endpoint devuelve algo coherente
            println("Logout del backend Supabase: ${backendResponse.message}")


            // 2. Cerrar sesión de Google (si el usuario usó Google para iniciar sesión)
            // Esto solo tiene efecto si hay una cuenta de Google activamente loggeada en la aplicación.
            googleSignInClient?.signOut()?.await()
            println("Google Sign-Out exitoso (si estaba loggeado con Google).")

            // 3. Limpiar cualquier estado de sesión almacenado localmente en Android.
            // ESTO ES CRUCIAL para que el usuario esté realmente "desloggeado" en el cliente,
            // independientemente de cómo inició sesión.
            sessionManager.clearSession() // <--- ¡LLAMA A ESTO!
            println("Sesión local limpiada.")

            true // Indica éxito
        } catch (e: Exception) {
            println("Error al cerrar sesión: ${e.message}")
            e.printStackTrace()
            false // Indica fallo
        }
    }
}