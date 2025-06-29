package com.Proyecto.coffeepalace.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_REFRESH_TOKEN = "refresh_token"
        const val KEY_USER_ID = "user_id"
        const val KEY_USER_EMAIL = "user_email" // <--- ¡NUEVA CLAVE!
        const val KEY_USER_NAME = "user_name"   // <--- ¡NUEVA CLAVE!
    }

    /**
     * Guarda la información de la sesión del usuario.
     * @param accessToken El token de acceso JWT de Supabase.
     * @param refreshToken El token de refresco de Supabase.
     * @param userId El ID único del usuario (UUID de Supabase Auth).
     * @param userEmail El correo electrónico del usuario.
     * @param userName El nombre del usuario (puede ser nulo si no está disponible).
     */
    fun saveSession(accessToken: String, refreshToken: String, userId: String, userEmail: String, userName: String?) { // <--- ¡NUEVOS PARÁMETROS!
        prefs.edit().apply {
            putString(KEY_ACCESS_TOKEN, accessToken)
            putString(KEY_REFRESH_TOKEN, refreshToken)
            putString(KEY_USER_ID, userId)
            putString(KEY_USER_EMAIL, userEmail) // <--- ¡GUARDAR EMAIL!
            putString(KEY_USER_NAME, userName)   // <--- ¡GUARDAR NOMBRE!
            apply()
        }
    }

    fun getAccessToken(): String? {
        return prefs.getString(KEY_ACCESS_TOKEN, null)
    }

    fun getUserId(): String? {
        return prefs.getString(KEY_USER_ID, null)
    }

    fun getUserEmail(): String? { // <--- Nuevo getter para el email
        return prefs.getString(KEY_USER_EMAIL, null)
    }

    fun getUserName(): String? { // <--- Nuevo getter para el nombre
        return prefs.getString(KEY_USER_NAME, null)
    }

    fun isLoggedIn(): Boolean {
        return prefs.getString(KEY_ACCESS_TOKEN, null) != null
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}