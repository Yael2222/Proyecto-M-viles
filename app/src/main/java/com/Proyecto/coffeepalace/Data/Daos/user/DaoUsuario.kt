package com.Proyecto.coffeepalace.Data.Daos.user

import com.Proyecto.coffeepalace.Data.Model.Client.Usuario

interface DaoUsuario {
    suspend fun getUserById(userId: Long): Usuario?
    suspend fun updateUserById(userId: Long, updatedUser: Usuario): Usuario?
}