package com.Proyecto.coffeepalace.Data.Repository


import com.Proyecto.coffeepalace.Data.Model.categoria
import com.Proyecto.coffeepalace.Data.Model.ingrediente
import com.Proyecto.coffeepalace.Data.Network.ApiService
import com.Proyecto.coffeepalace.Data.Network.SearchResponse
import android.util.Log // Para depuración

import com.Proyecto.coffeepalace.Data.Repository.Result

class SearchRepository(
    private val apiService: ApiService,
    private val ingredienteRepository: IngredienteRepository, // Para obtener todos los ingredientes disponibles
    private val categoryRepository: CategoryRepository // Para obtener todas las categorías disponibles
) {
    suspend fun searchItems(
        query: String?,
        categoryId: Long?,
        ingredientIds: List<Long>?
    ): Result<SearchResponse> { // Usa tu sealed class Result personalizada
        return try {
            val ingredientsString = ingredientIds?.joinToString(",") // Convierte la lista de IDs a una cadena "1,2,3"
            val response = apiService.searchItems(query, categoryId, ingredientsString)
            Result.Success(response)
        } catch (e: Exception) {
            Log.e("SearchRepository", "Error searching items: ${e.message}", e)
            Result.Error(e)
        }
    }

    // Métodos para obtener los datos necesarios para los filtros de la UI
    suspend fun getAvailableIngredients(): List<ingrediente> {
        return ingredienteRepository.getAllIngredientes()
    }

    suspend fun getAvailableCategories(): List<categoria> {
        return categoryRepository.getAllCategories()
    }
}