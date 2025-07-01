package com.Proyecto.coffeepalace.Data.Daos.ads

import com.Proyecto.coffeepalace.Data.Model.ads
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class DaoAdsImpl : DaoAds {

    private val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://gcdxyzsgpkmmiemvaskx.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdjZHh5enNncGttbWllbXZhc2t4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDgyMDgzODcsImV4cCI6MjA2Mzc4NDM4N30.ooXN38ZDnjEWd4uzaZJsoG8IzfERlX009nVqh9ggpq0"
    ) {
        install(Postgrest)
    }

    override suspend fun getAllAds(): List<ads> {
        return supabase.postgrest
            .from("anuncio")
            .select()
            .decodeList<ads>()
    }

    override suspend fun addAds(description: String, image: String, name: String): Boolean {
        return try {
            val json = buildJsonObject {
                put("descripcion", description)
                put("imagen", image)
                put("titulo", name)
            }

            supabase.postgrest
                .from("anuncio")
                .insert(json)
            true
        } catch (e: Exception) {
            println("Error adding ad: ${e.message}")
            false
        }
    }

    override suspend fun deleteAds(id: Long): Boolean {
        return try {
            supabase.postgrest
                .from("anuncio")
                .delete {
                    eq("id", id)
                }
            true
        } catch (e: Exception) {
            println("Error deleting ad: ${e.message}")
            false
        }
    }
}
