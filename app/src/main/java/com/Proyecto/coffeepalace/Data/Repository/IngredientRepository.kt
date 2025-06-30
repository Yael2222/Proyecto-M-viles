package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Model.Ingredient
import com.Proyecto.coffeepalace.Data.Remote.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

class IngredientRepository {
    private val client = SupabaseClient.client
    suspend fun getIngredientesByReceta(idReceta: Int): List<Ingredient> = withContext(Dispatchers.IO) {
        try {
            val recetaIngredientes = client.from("receta_ingrediente")
                .select {
                    filter {
                        eq("id_receta", idReceta)
                    }
                }
                .decodeList<RecetaIngredienteDto>()

            val idsIngredientes = recetaIngredientes.map { it.id_ingrediente }
            if (idsIngredientes.isEmpty()) return@withContext emptyList()

            val ingredientes = client.from("ingrediente")
                .select {
                    filter {
                        inFilter("id", idsIngredientes)
                    }
                }
                .decodeList<IngredientDto>()

            ingredientes.map { dto -> Ingredient(id = dto.id, nombre = dto.nombre) }

        } catch (e: Exception) {
            println("Error loading ingredientes: ${e.message}")
            emptyList()
        }
    }

    @Serializable
    data class RecetaIngredienteDto(val id_ingrediente: Int)

    @Serializable
    data class IngredientDto(val id: Int, val nombre: String)
}
