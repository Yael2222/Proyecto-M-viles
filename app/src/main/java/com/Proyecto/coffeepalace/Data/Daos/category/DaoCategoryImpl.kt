package com.Proyecto.coffeepalace.Data.Daos.category

import com.Proyecto.coffeepalace.Data.Model.categoria
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class DaoCategoryImpl : DaoCategory {

    private val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://gcdxyzsgpkmmiemvaskx.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdjZHh5enNncGttbWllbXZhc2t4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDgyMDgzODcsImV4cCI6MjA2Mzc4NDM4N30.ooXN38ZDnjEWd4uzaZJsoG8IzfERlX009nVqh9ggpq0"
    ) {

        install(Postgrest)
    }

    override suspend fun getAllCategories(): List<categoria> {
        return supabase.postgrest
            .from("categoria")
            .select()
            .decodeList<categoria>()
    }


    override suspend fun addCategory(nombre: String): Boolean {
        return try {
            val json = buildJsonObject {
                put("nombre", nombre)
            }
            supabase.postgrest
                .from("categoria")
                .insert(json)
            true
        } catch (e: Exception) {
            println("Error adding category: ${e.message}")
            false
        }
    }


    override suspend fun deleteCategory(id: Long): Boolean {
        return try {
            supabase.postgrest
                .from("categoria")
                .delete {
                    eq("id", id)
                }
            true
        } catch (e: Exception) {
            println("Error deleting category: ${e.message}")
            false
        }
    }
}
