package com.Proyecto.coffeepalace.Data.Daos.ingrediente

import com.Proyecto.coffeepalace.Data.Model.ingrediente


interface DaoIngrediente {
        suspend fun getAllIngredientes(): List<ingrediente>
        suspend fun addIngrediente(nombre: String): Boolean
        suspend fun deleteIngrediente(id: Long): Boolean
    }