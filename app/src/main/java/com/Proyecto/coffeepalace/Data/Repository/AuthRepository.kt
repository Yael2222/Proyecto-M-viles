package com.Proyecto.coffeepalace.Data.Repository

import android.content.Intent
import android.util.Log
import com.Proyecto.coffeepalace.Data.Model.AuthResponse
import com.Proyecto.coffeepalace.Data.Model.usuario
import com.Proyecto.coffeepalace.Data.Network.ApiService
import com.Proyecto.coffeepalace.Data.Network.AuthRequest
import com.Proyecto.coffeepalace.Data.Network.ResetPasswordRequest
import com.Proyecto.coffeepalace.Data.Network.SignUpRequest
import com.Proyecto.coffeepalace.Data.Network.GoogleSignInTokenRequest
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.Proyecto.coffeepalace.utils.SessionManager
import com.google.android.gms.tasks.Task

class AuthRepository(
    private val apiService: ApiService,
    private val googleSignInClient: GoogleSignInClient?,
    private val sessionManager: SessionManager
) {
    suspend fun signUp(email: String, password: String, name: String): AuthResponse {
        val response = apiService.signUp(SignUpRequest(email, password, name))
        response.session?.let { session ->
            // Al registrarse, el nombre podría estar en user_metadata. Si no, usa el 'name' de la entrada.
            val userName = session.user.userMetadata?.get("name") as? String ?: name
            sessionManager.saveSession(
                accessToken = session.accessToken,
                refreshToken = session.refreshToken,
                userId = session.user.id, // Auth ID
                userEmail = session.user.email,
                userName = userName
            )
        }
        return response
    }


    suspend fun signIn(email: String, password: String): AuthResponse {
        val response = apiService.signIn(AuthRequest(email, password))
        response.session?.let { session ->
            val userName = session.user.userMetadata?.get("name") as? String
            sessionManager.saveSession(
                accessToken = session.accessToken,
                refreshToken = session.refreshToken,
                userId = session.user.id, // Auth ID
                userEmail = session.user.email,
                userName = userName
            )
        }
        return response
    }

    suspend fun resetPassword(email: String): AuthResponse {
        return apiService.resetPassword(ResetPasswordRequest(email))
    }

    fun getGoogleSignInIntent() = googleSignInClient?.signInIntent

    suspend fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>): String? {
        return try {

            val account = task.await()
            account?.idToken
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error al manejar el resultado de Google Sign-In", e)
            null // Devolvemos null en caso de error
        }
    }

    suspend fun signInWithGoogleOnBackend(idToken: String): AuthResponse {
        val response = apiService.signInWithGoogleToken(GoogleSignInTokenRequest(idToken))
        response.session?.let { session ->
            val userName = session.user.userMetadata?.get("name") as? String
            sessionManager.saveSession(
                accessToken = session.accessToken,
                refreshToken = session.refreshToken,
                userId = session.user.id, // Auth ID
                userEmail = session.user.email,
                userName = userName
            )
        }
        return response
    }

    suspend fun signOut(): Boolean {
        return try {
            try {

                apiService.signOutBackend()
                println("Logout del backend Supabase exitoso (si implementado).")
            } catch (e: Exception) {
                Log.e("AuthRepository", "Error al llamar al endpoint de logout del backend: ${e.message}", e)
                println("Error al llamar al endpoint de logout del backend: ${e.message}. Continuando con el logout local.")
            }

            googleSignInClient?.signOut()?.await()
            println("Google Sign-Out exitoso (si estaba loggeado con Google).")

            sessionManager.clearSession()
            println("Sesión local limpiada.")

            true
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error al cerrar sesión: ${e.message}", e)
            println("Error al cerrar sesión: ${e.message}")
            false
        }
    }

    fun isLoggedIn(): Boolean {
        return sessionManager.isLoggedIn()
    }

    fun getCurrentUserEmail(): String? {
        return sessionManager.getUserEmail()
    }

    fun getCurrentUserName(): String? {
        return sessionManager.getUserName()
    }

    fun getCurrentAuthId(): String? { // Nuevo para obtener el ID de autenticación
        return sessionManager.getUserId()
    }

    suspend fun getUsuarioProfileFromBackend(email: String): usuario? { // <--- Ahora recibe 'email'
        return try {
            val profile = apiService.getUsuarioByEmail(email) // <--- Llama a la nueva función
            Log.d("AuthRepository", "Perfil de usuario obtenido: $profile")
            profile
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error fetching user profile from backend for email: $email - ${e.message}", e) // <--- Mensaje actualizado
            null
        }
    }




}