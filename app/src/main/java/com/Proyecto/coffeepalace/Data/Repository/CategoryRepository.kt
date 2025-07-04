package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Daos.category.DaoCategory
import com.Proyecto.coffeepalace.Data.Model.categoria
import com.Proyecto.coffeepalace.Data.Network.ApiService

class CategoryRepository(private val apiService: ApiService) : DaoCategory {

    override suspend fun getAllCategories(): List<categoria> {
        return try {
            apiService.getAllCategories()
        } catch (e: Exception) {
            println("Error fetching categories from backend: ${e.message}")
            emptyList()
        }
    }

    override suspend fun addCategory(name: String): Boolean {
        return try {
            // Ahora puedes crear un objeto categoria sin el ID, ya que es nullable.
            val categoryToAdd = categoria(nombre = name)
            val response = apiService.addCategory(categoryToAdd)
            // Comprueba si la respuesta del backend tiene un ID asignado, lo que indica éxito.
            response.id != null
        } catch (e: Exception) {
            println("Error adding category to backend: ${e.message}")
            false
        }
    }

    override suspend fun deleteCategory(id: Long): Boolean {
        return try {
            apiService.deleteCategory(id)
            true
        } catch (e: Exception) {
            println("Error deleting category from backend: ${e.message}")
            false
        }
    }
}