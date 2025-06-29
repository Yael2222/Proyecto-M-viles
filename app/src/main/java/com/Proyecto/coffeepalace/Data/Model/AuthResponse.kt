package com.Proyecto.coffeepalace.Data.Model

import com.google.gson.annotations.SerializedName

// Respuesta básica de éxito/error del backend
data class AuthResponse(
    @SerializedName("message") val message: String?,
    @SerializedName("user") val user: UserResponse?,
    @SerializedName("session") val session: SessionResponse?
)



// Representación básica de la sesión de Supabase Auth
data class SessionResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("expires_in") val expiresIn: Long,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("user") val user: UserResponse
)
data class UserResponse(
    @SerializedName("id") val id: String, // UID del usuario en Supabase Auth
    @SerializedName("email") val email: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("user_metadata") val userMetadata: Map<String, Any>? = null // <--- ¡AÑADIR ESTO!
    // Puedes añadir más campos como 'email_confirmed_at', 'role', 'user_metadata' si los necesitas
)