package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Daos.ingrediente.DaoIngrediente
import com.Proyecto.coffeepalace.Data.Model.ingrediente
import com.Proyecto.coffeepalace.Data.Network.ApiService

class IngredienteRepository(private val apiService: ApiService) : DaoIngrediente {

    override suspend fun getAllIngredientes(): List<ingrediente> {
        return try {
            apiService.getAllIngredientes()
        } catch (e: Exception) {
            println("Error fetching ingredients from backend: ${e.message}")
            emptyList()
        }
    }

    override suspend fun addIngrediente(nombre: String): Boolean {
        return try {
            // Crea un objeto ingrediente sin ID, ya que es nullable y el backend lo asignará.
            val ingredienteToAdd = ingrediente(nombre = nombre)
            val response = apiService.addIngrediente(ingredienteToAdd)
            // Comprueba si la respuesta del backend contiene un ID asignado, lo que indica éxito.
            response.id != null
        } catch (e: Exception) {
            println("Error adding ingredient to backend: ${e.message}")
            false
        }
    }

    override suspend fun deleteIngrediente(id: Long): Boolean {
        return try {
            apiService.deleteIngrediente(id)
            true // Si no hay excepción, asumimos éxito (el backend devuelve 204 No Content)
        } catch (e: Exception) {
            println("Error deleting ingredient from backend: ${e.message}")
            false
        }
    }
}