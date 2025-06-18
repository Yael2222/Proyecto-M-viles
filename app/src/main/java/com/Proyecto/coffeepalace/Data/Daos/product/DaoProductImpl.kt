package com.Proyecto.coffeepalace.Data.Daos.product

import com.Proyecto.coffeepalace.Data.Model.producto
import com.Proyecto.coffeepalace.Data.Model.categoria
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest

class DaoProductImpl : DaoProducto {

    private val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://gcdxyzsgpkmmiemvaskx.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdjZHh5enNncGttbWllbXZhc2t4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDgyMDgzODcsImV4cCI6MjA2Mzc4NDM4N30.ooXN38ZDnjEWd4uzaZJsoG8IzfERlX009nVqh9ggpq0"
    ) {
        install(Postgrest)
    }

    override suspend fun addProducto(producto: producto): Boolean {
        return try {
            supabase.postgrest
                .from("producto")
                .insert(producto)
            true
        } catch (e: Exception) {
            println("Error adding product: ${e.message}")
            false
        }
    }

    override suspend fun getCategoriasProducto(): List<categoria> {
        return try {
            supabase.postgrest
                .from("categoria")
                .select()
                .decodeList<categoria>()
        } catch (e: Exception) {
            println("Error fetching categories: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getProductos(): List<producto> {
        return try {
            supabase.postgrest
                .from("producto")
                .select()
                .decodeList<producto>()
        } catch (e: Exception) {
            println("Error fetching products: ${e.message}")
            emptyList()
        }
    }




    override suspend fun deleteProducto(id: Long?): Boolean {
        return try {
            id?.let {
                supabase.postgrest
                    .from("producto")
                    .delete {
                        eq("id", it)
                    }
                true
            } ?: false // Si el ID es null, no hace nada y devuelve false
        } catch (e: Exception) {
            println("Error deleting product: ${e.message}")
            false
        }
    }

}

