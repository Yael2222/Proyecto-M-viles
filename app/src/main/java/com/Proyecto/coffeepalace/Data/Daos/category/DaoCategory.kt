package com.Proyecto.coffeepalace.Data.Daos.category

import com.Proyecto.coffeepalace.Data.Model.Categoria

interface DaoCategory {
    suspend fun getAllCategories(): List<Categoria>
    suspend fun getCategoryById(id: Long): Categoria?
    suspend fun addCategory(name: String): Boolean
    suspend fun deleteCategory(id: Long): Boolean
}
