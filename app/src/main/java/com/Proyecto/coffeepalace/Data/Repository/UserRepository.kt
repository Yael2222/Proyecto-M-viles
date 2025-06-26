package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Daos.usuario.DaoUsuario
import com.Proyecto.coffeepalace.Data.Model.usuario
import com.Proyecto.coffeepalace.Data.Network.ApiService

class UserRepository(private val apiService: ApiService) : DaoUsuario {

    override suspend fun getAllUsers(): List<usuario> {
        return try {
            apiService.getAllUsers()
        } catch (e: Exception) {
            println("Error fetching users from backend: ${e.message}")
            emptyList()
        }
    }
}