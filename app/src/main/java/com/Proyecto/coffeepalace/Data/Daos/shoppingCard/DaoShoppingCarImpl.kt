package com.Proyecto.coffeepalace.Data.Daos.shoppingCard

import com.Proyecto.coffeepalace.Data.Model.Client.Carrito
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class DaoShoppingCarImpl : DaoShoppingCar {

    private data object Table {
        const val name = "carrito"
    }

    private val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://gcdxyzsgpkmmiemvaskx.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdjZHh5enNncGttbWllbXZhc2t4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDgyMDgzODcsImV4cCI6MjA2Mzc4NDM4N30.ooXN38ZDnjEWd4uzaZJsoG8IzfERlX009nVqh9ggpq0"
    ) {

        install(Postgrest)
    }

    override suspend fun getAllOwnShoppingCar(userId: Long): List<CarritoProductos> {
        return try {
            supabase.postgrest
                .from(Table.name)
                .select(columns = Columns.raw("id, id_cliente, producto(*)")) {
                    eq("id_cliente", userId)
                }
                .decodeList<CarritoProductos>()
        } catch (e: Exception) {
            println("Error deleting category: ${e.message}")
            listOf<CarritoProductos>()
        }
    }

    override suspend fun getOwnShoppingCarById(shoppingCarId: Long): CarritoProductos? {
        return supabase.postgrest
            .from(Table.name)
            .select(single = true) {
                eq("id", shoppingCarId)
            }
            .decodeSingleOrNull<CarritoProductos>()
    }

    override suspend fun addProductToShoppingCar(productId: Long, clientId: Long): Boolean {
        return try {
            val json = buildJsonObject {
                put("id_producto", productId)
                put("id_usuario", clientId)
            }
            supabase.postgrest
                .from(Table.name)
                .insert(json)
            true
        } catch (e: Exception) {
            println("Error adding product car: ${e.message}")
            false
        }
    }

    override suspend fun deleteProductFromShoppingCar(shoppingCarId: Long): Boolean {
        return try {
            val productTobeUpdated = supabase.postgrest
                .from(Table.name)
                .select(single = true) {
                    eq("id", shoppingCarId)
                }.decodeSingleOrNull<Carrito>()
            if (productTobeUpdated == null) return false

            supabase.postgrest
                .from(Table.name)
                .delete {
                    eq("id", shoppingCarId)
                }
            true
        } catch (e: Exception) {
            println("Error deleting product car: ${e.message}")
            false
        }
    }
}