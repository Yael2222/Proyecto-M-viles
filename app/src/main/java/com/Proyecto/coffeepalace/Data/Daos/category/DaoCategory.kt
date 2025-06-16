package com.Proyecto.coffeepalace.Data.Daos.category

import com.Proyecto.coffeepalace.Data.Model.categoria

interface DaoCategory {
    suspend fun getAllCategories(): List<categoria>
    suspend fun addCategory(name: String): Boolean
    suspend fun deleteCategory(id: Long): Boolean
}
