package com.Proyecto.coffeepalace.Data

/*
import android.provider.ContactsContract.CommonDataKinds.Email
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.user.UserInfo

class AuthRepository {
    private val auth: Auth get() = SupabaseClientHolder.client.gotrue

    suspend fun login(email: String, password: String): Result<UserInfo> = runCatching {
        auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        auth.currentUserOrNull() ?: throw Exception("User not found")
    }

    suspend fun register(email: String, password: String): Result<Unit> = runCatching {
        auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
    }

    suspend fun resetPassword(email: String): Result<Unit> = runCatching {
        auth.resetPasswordForEmail(email)
    }

    suspend fun logout(): Result<Unit> = runCatching {
        auth.signOut()
    }

    fun getCurrentUser(): UserInfo? = auth.currentUserOrNull()
}*/