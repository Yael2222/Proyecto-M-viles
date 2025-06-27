package com.Proyecto.coffeepalace.Data.Daos.ingrediente

import com.Proyecto.coffeepalace.Data.Model.ingrediente
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class DaoIngredienteImpl : DaoIngrediente {

    private val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://gcdxyzsgpkmmiemvaskx.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdjZHh5enNncGttbWllbXZhc2t4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDgyMDgzODcsImV4cCI6MjA2Mzc4NDM4N30.ooXN38ZDnjEWd4uzaZJsoG8IzfERlX009nVqh9ggpq0"
    ) {
        install(Postgrest)
    }

    override suspend fun getAllIngredientes(): List<ingrediente> {
        return try {
            supabase.postgrest
                .from("ingrediente")
                .select()
                .decodeList<ingrediente>()
        } catch (e: Exception) {
            println("Error fetching ingredientes: ${e.message}")
            emptyList()
        }
    }

    override suspend fun addIngrediente(nombre: String): Boolean {
        return try {
            val json = buildJsonObject {
                put("nombre", nombre)
            }
            supabase.postgrest
                .from("ingrediente")
                .insert(json)
            true
        } catch (e: Exception) {
            println("Error adding ingrediente: ${e.message}")
            false
        }
    }

    override suspend fun deleteIngrediente(id: Long): Boolean {
        return try {
            supabase.postgrest
                .from("ingrediente")
                .delete {
                    eq("id", id)
                }
            true
        } catch (e: Exception) {
            println("Error deleting ingrediente: ${e.message}")
            false
        }
    }
}
