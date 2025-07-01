package com.Proyecto.coffeepalace.Data.Daos.usuario

import com.Proyecto.coffeepalace.Data.Model.usuario

interface DaoUsuario {
    suspend fun getAllUsers(): List<usuario>
}