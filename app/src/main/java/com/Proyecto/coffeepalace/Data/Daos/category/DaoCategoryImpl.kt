package com.Proyecto.coffeepalace.Data.Daos.category

import com.Proyecto.coffeepalace.Data.Daos.SupabaseProvider
import com.Proyecto.coffeepalace.Data.Model.Categoria
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class DaoCategoryImpl : DaoCategory {

    private val supabase = SupabaseProvider.supabase

    override suspend fun getAllCategories(): List<Categoria> {
        return supabase.postgrest
            .from("categoria")
            .select()
            .decodeList<Categoria>()
    }

    override suspend fun getCategoryById(id: Long): Categoria? {
        return supabase.postgrest
            .from("categoria")
            .select() {
                eq("id", id)
            }
            .decodeSingle<Categoria>()
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
