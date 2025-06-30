package com.Proyecto.coffeepalace.Data.Daos.Busqueda

import com.Proyecto.coffeepalace.Data.Model.receta
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest

class DaoRecetaImpl : DaoReceta {

    private val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://gcdxyzsgpkmmiemvaskx.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdjZHh5enNncGttbWllbXZhc2t4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDgyMDgzODcsImV4cCI6MjA2Mzc4NDM4N30.ooXN38ZDnjEWd4uzaZJsoG8IzfERlX009nVqh9ggpq0"
    ) {
        install(Postgrest)
    }

    override suspend fun buscarPorNombreOIngrediente(query: String): List<receta> {
        return try {
            val nombre = query.lowercase()
            supabase.postgrest.from("receta")
                .select()
                .decodeList<receta>()
                .filter {
                    it.nombre.lowercase().contains(nombre) || it.descripcion.lowercase().contains(nombre)
                }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
