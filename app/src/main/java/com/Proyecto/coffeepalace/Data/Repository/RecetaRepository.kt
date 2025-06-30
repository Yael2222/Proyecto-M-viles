package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Model.receta
import io.github.jan.supabase.postgrest.from

class RecetaRepository {

    suspend fun getAllRecetas(): List<receta> {
        return SupabaseManager.client
            .from("receta")
            .select()
            .decodeList<receta>()
    }

    suspend fun searchRecetas(query: String): List<receta> {
        return SupabaseManager.client
            .from("receta")
            .select()
            .decodeList<receta>()
            .filter {
                it.nombre.contains(query, ignoreCase = true) ||
                        it.descripcion.contains(query, ignoreCase = true)
            }
    }
}
