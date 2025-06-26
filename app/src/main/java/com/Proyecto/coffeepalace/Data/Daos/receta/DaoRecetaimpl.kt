package com.Proyecto.coffeepalace.Data.Daos.receta

import com.Proyecto.coffeepalace.Data.Model.ingrediente
import com.Proyecto.coffeepalace.Data.Model.receta
import com.Proyecto.coffeepalace.Data.Model.receta_ingrediente
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class DaoRecetaImpl : DaoReceta {

    private val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://gcdxyzsgpkmmiemvaskx.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdjZHh5enNncGttbWllbXZhc2t4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDgyMDgzODcsImV4cCI6MjA2Mzc4NDM4N30.ooXN38ZDnjEWd4uzaZJsoG8IzfERlX009nVqh9ggpq0"
    ) {
        install(Postgrest)
    }

    override suspend fun getAllIngredientesReceta(): List<ingrediente> {
        return try {
            supabase.postgrest
                .from("ingrediente")
                .select()
                .decodeList<ingrediente>()
        } catch (e: Exception) {
            println("Error al obtener ingredientes: ${e.message}")
            emptyList()
        }
    }

    override suspend fun addReceta(receta: receta, ingredientesIds: List<Long>): Boolean {
        return try {
            val recetaJson = buildJsonObject {
                put("nombre", receta.nombre)
                put("descripcion", receta.descripcion)
                put("instrucciones", receta.instrucciones)
                put("imagen", receta.imagen)
            }

            val insertedReceta = supabase.postgrest
                .from("receta")
                .insert(recetaJson)
                .decodeSingle<receta>()
            val recetaId = insertedReceta.id
            receta.id = recetaId
            val ingredientesJsonArray = buildJsonArray {
                ingredientesIds.forEach { idIngrediente ->
                    add(buildJsonObject {
                        put("id_ingrediente", idIngrediente)
                        put("id_receta", recetaId)
                    })
                }
            }

            supabase.postgrest
                .from("receta_ingrediente")
                .insert(ingredientesJsonArray)

            true
        } catch (e: Exception) {
            println("Error al insertar receta o ingredientes: ${e.message}")
            false
        }
    }

    override suspend fun getAllRecetas(): List<receta> {
        return try {
            supabase.postgrest
                .from("receta")
                .select()
                .decodeList<receta>()
        } catch (e: Exception) {
            println("Error al obtener recetas: ${e.message}")
            emptyList()
        }
    }

    // --- NUEVA IMPLEMENTACIÓN: Obtener todas las relaciones de receta_ingrediente ---
    override suspend fun getAllRecetaIngredienteRelations(): List<receta_ingrediente> {
        return try {
            supabase.postgrest
                .from("receta_ingrediente") // Nombre de tu tabla pivote
                .select()
                .decodeList<receta_ingrediente>()
        } catch (e: Exception) {
            println("Error al obtener relaciones receta_ingrediente: ${e.message}")
            emptyList()
        }
    }

    override suspend fun deleteReceta(idReceta: Long): Boolean {
        return try {
            // Primero, elimina las entradas relacionadas en `receta_ingrediente`
            supabase.postgrest
                .from("receta_ingrediente")
                .delete {
                    eq("id_receta", idReceta)
                }

            // Luego, elimina la receta de la tabla 'receta'
            supabase.postgrest
                .from("receta")
                .delete {
                    eq("id", idReceta)
                }
            true
        } catch (e: Exception) {
            println("Error al eliminar receta: ${e.message}")
            false
        }
    }
}