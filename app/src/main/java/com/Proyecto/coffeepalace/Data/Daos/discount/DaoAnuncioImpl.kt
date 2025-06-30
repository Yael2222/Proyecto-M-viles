package com.Proyecto.coffeepalace.Data.Daos.discount

import com.Proyecto.coffeepalace.Data.Model.Anuncio
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest

class DaoAnuncioImpl : DaoAnuncio {
    private data object Table {
        const val name = "anuncio"
    }

    private val supabase: SupabaseClient = createSupabaseClient(

        supabaseUrl = "https://gcdxyzsgpkmmiemvaskx.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdjZHh5enNncGttbWllbXZhc2t4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDgyMDgzODcsImV4cCI6MjA2Mzc4NDM4N30.ooXN38ZDnjEWd4uzaZJsoG8IzfERlX009nVqh9ggpq0"
    ) {

        install(Postgrest)
    }

    override suspend fun getAllAnuncios(): List<Anuncio> {
        return try {
            supabase.postgrest
                .from(Table.name)
                .select()
                .decodeList<Anuncio>()
            listOf<Anuncio>()
        } catch (e: Exception) {
            println("Error getting anuncios: ${e.message}")
            listOf<Anuncio>()
        }
    }
}
