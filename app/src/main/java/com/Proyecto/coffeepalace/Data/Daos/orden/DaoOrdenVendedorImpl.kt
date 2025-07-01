package com.Proyecto.coffeepalace.Data.Daos.orden

import com.Proyecto.coffeepalace.Data.Model.FacturaConEstado
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest

class DaoOrdenVendedorImpl : DaoOrdenVendedor {

    private val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://gcdxyzsgpkmmiemvaskx.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdjZHh5enNncGttbWllbXZhc2t4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDgyMDgzODcsImV4cCI6MjA2Mzc4NDM4N30.ooXN38ZDnjEWd4uzaZJsoG8IzfERlX009nVqh9ggpq0"
    ) {
        install(Postgrest)
    }

    override suspend fun getAllOrdenVendedor(): List<FacturaConEstado> {
        return try {
            supabase.postgrest
                .from("orden_vendedor")
                .select()
                .decodeList<FacturaConEstado>()
        } catch (e: Exception) {
            println("Error al obtener ordenVendedor: ${e.message}")
            emptyList()
        }
    }
}