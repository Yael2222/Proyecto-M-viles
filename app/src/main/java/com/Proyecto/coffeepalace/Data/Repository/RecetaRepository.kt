package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Daos.receta.DaoReceta // Tu interfaz DaoReceta
import com.Proyecto.coffeepalace.Data.Model.RecetaWithIngredientes
import com.Proyecto.coffeepalace.Data.Model.ingrediente
import com.Proyecto.coffeepalace.Data.Model.receta
import com.Proyecto.coffeepalace.Data.Model.receta_ingrediente
import com.Proyecto.coffeepalace.Data.Network.AddRecetaRequestBody
import com.Proyecto.coffeepalace.Data.Network.ApiService

class RecetaRepository(private val apiService: ApiService) : DaoReceta {

    override suspend fun getAllIngredientesReceta(): List<ingrediente> {
        return try {
            apiService.getAllIngredientesReceta()
        } catch (e: Exception) {
            println("Error fetching ingredientes para receta desde backend: ${e.message}")
            emptyList()
        }
    }
    override suspend fun getRecetaByIdWithIngredientes(idReceta: Long): RecetaWithIngredientes? {
        return try {
            // Retrofit ahora deserializará directamente a RecetaWithIngredientes
            apiService.getRecetaByIdWithIngredientes(idReceta)
        } catch (e: Exception) {
            println("Error fetching receta con ingredientes desde backend para ID $idReceta: ${e.message}")
            e.printStackTrace()
            null
        }
    }
    override suspend fun addReceta(receta: receta, ingredientesIds: List<Long>): Boolean {
        return try {
            val requestBody = AddRecetaRequestBody(receta, ingredientesIds)
            val response = apiService.addReceta(requestBody)
            response.id != null // Asume éxito si el backend devuelve la receta con ID
        } catch (e: Exception) {
            println("Error añadiendo receta a backend: ${e.message}")
            e.printStackTrace() // Para ver la traza completa
            false
        }
    }

    override suspend fun getAllRecetas(): List<receta> {
        return try {
            apiService.getAllRecetas()
        } catch (e: Exception) {
            println("Error fetching recetas desde backend: ${e.message}")
            emptyList()
        }
    }

    override suspend fun deleteReceta(idReceta: Long?): Boolean {
        return try {
            val response = apiService.deleteReceta(idReceta)
            response.isSuccessful // Verifica si el HTTP 204 No Content fue exitoso
        } catch (e: Exception) {
            println("Error eliminando receta desde backend: ${e.message}")
            e.printStackTrace() // Para ver la traza completa
            false
        }
    }

    override suspend fun getAllRecetaIngredienteRelations(): List<receta_ingrediente> {
        return try {
            apiService.getAllRecetaIngredienteRelations()
        } catch (e: Exception) {
            println("Error fetching relaciones receta-ingrediente desde backend: ${e.message}")
            emptyList()
        }
    }
}