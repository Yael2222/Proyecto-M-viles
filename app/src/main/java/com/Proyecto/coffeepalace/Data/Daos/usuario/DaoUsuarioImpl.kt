package com.Proyecto.coffeepalace.Data.Daos.usuario

import com.Proyecto.coffeepalace.Data.Model.usuario
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest

class DaoUsuarioImpl : DaoUsuario {


    private val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://gcdxyzsgpkmmiemvaskx.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdjZHh5enNncGttbWllbXZhc2t4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDgyMDgzODcsImV4cCI6MjA2Mzc4NDM4N30.ooXN38ZDnjEWd4uzaZJsoG8IzfERlX009nVqh9ggpq0"
    ) {
        install(Postgrest)
    }

    override suspend fun getAllUsers(): List<usuario> {
        return try {

            supabase.postgrest
                .from("usuario")
                .select()
                .decodeList<usuario>()
        } catch (e: Exception) {

            println("Error al obtener usuarios de Supabase: ${e.message}")
            emptyList()
        }
    }

    /*
    override suspend fun addUser(user: Usuario): Boolean {
        return try {
            // Puedes usar kotlinx.serialization.json.buildJsonObject para construir el cuerpo de la solicitud
            // o simplemente pasar el objeto 'user' directamente si está configurado para ello.
            supabase.postgrest
                .from("usuario")
                .insert(user) // Si el objeto Usuario es directamente serializable a JSON
            true
        } catch (e: Exception) {
            println("Error al añadir usuario: ${e.message}")
            false
        }
    }

    override suspend fun deleteUser(id: Int): Boolean {
        return try {
            supabase.postgrest
                .from("usuario")
                .delete {
                    eq("id", id) // Elimina el usuario donde 'id' coincide
                }
            true
        } catch (e: Exception) {
            println("Error al eliminar usuario: ${e.message}")
            false
        }
    }
    */
}