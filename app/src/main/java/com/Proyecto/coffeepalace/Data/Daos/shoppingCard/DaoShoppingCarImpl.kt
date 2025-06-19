package com.Proyecto.coffeepalace.Data.Daos.shoppingCard

import com.Proyecto.coffeepalace.Data.Model.Client.Carrito
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest

class DaoShoppingCarImpl : DaoShoppingCar {

    private val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://gcdxyzsgpkmmiemvaskx.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdjZHh5enNncGttbWllbXZhc2t4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDgyMDgzODcsImV4cCI6MjA2Mzc4NDM4N30.ooXN38ZDnjEWd4uzaZJsoG8IzfERlX009nVqh9ggpq0"
    ) {

        install(Postgrest)
    }

    override suspend fun getAllOwnShoppingCar(): List<Carrito> {
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

override suspend fun addProductToShoppingCar(productId: Int): Boolean {
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

override suspend fun deleteProductFromShoppingCar(productId: Int): Boolean {
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