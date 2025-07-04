package com.Proyecto.coffeepalace.Data.Daos.product

import com.Proyecto.coffeepalace.Data.Model.Categoria
import com.Proyecto.coffeepalace.Data.Model.Producto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest

class DaoProductoImpl : DaoProducto {

    private data object Table {
        const val name = "producto"
    }

    private val supabase: SupabaseClient = createSupabaseClient(

        supabaseUrl = "https://gcdxyzsgpkmmiemvaskx.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdjZHh5enNncGttbWllbXZhc2t4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDgyMDgzODcsImV4cCI6MjA2Mzc4NDM4N30.ooXN38ZDnjEWd4uzaZJsoG8IzfERlX009nVqh9ggpq0"
    ) {

        install(Postgrest)
    }

    override suspend fun addProducto(producto: Producto): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoriasProducto(): List<Categoria> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductos(): List<Producto> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProducto(id: Long?): Boolean {
        TODO("Not yet implemented")
    }


    override suspend fun getAllProducts(): List<Producto> {
        return try {
            supabase.postgrest
                .from(Table.name)
                .select()
                .decodeList<Producto>()
        } catch (e: Exception) {
            println("Error getting all productos: ${e.message}")
            listOf<Producto>()
        }
    }

    override suspend fun getProductsByCategory(categoryId: Long): List<Producto> {
        return try {
            supabase.postgrest
                .from(Table.name)
                .select() {
                    eq("categoria", categoryId)
                }
                .decodeList<Producto>()
        } catch (e: Exception) {
            println("Error getting all productos: ${e.message}")
            listOf<Producto>()
        }
    }
}