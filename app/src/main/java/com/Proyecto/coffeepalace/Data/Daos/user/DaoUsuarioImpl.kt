package com.Proyecto.coffeepalace.Data.Daos.user

import com.Proyecto.coffeepalace.Data.Model.Client.Usuario
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest

class DaoUsuarioImpl : DaoUsuario {
    private data object Table {
        const val name = "usuario"
    }

    private val supabase: SupabaseClient = createSupabaseClient(

        supabaseUrl = "https://gcdxyzsgpkmmiemvaskx.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdjZHh5enNncGttbWllbXZhc2t4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDgyMDgzODcsImV4cCI6MjA2Mzc4NDM4N30.ooXN38ZDnjEWd4uzaZJsoG8IzfERlX009nVqh9ggpq0"
    ) {

        install(Postgrest)
    }

    override suspend fun getUserById(userId: Long): Usuario? {
        return try {
            supabase.postgrest
                .from(Table.name)
                .select(){
                    eq("id", userId)
                }
                .decodeSingleOrNull<Usuario>()
        } catch (e: Exception) {
            println("Error getting user: ${e.message}")
            null
        }
    }

    override suspend fun updateUserById(
        userId: Long,
        updatedUser: Usuario
    ): Usuario? {
        return try {
            val userTobeUpdated = supabase.postgrest
                .from(Table.name)
                .select(
                    single = true
                ) {
                    eq("id", userId)
                }
                .decodeAsOrNull<Usuario>()

            if (userTobeUpdated == null) return null

            return supabase.postgrest
                .from(Table.name)
                .update(updatedUser) {
                    eq("id", userId)
                }
                .decodeSingle<Usuario>()

        } catch (e: Exception) {
            println("Error updating user: ${e.message}")
            null
        }

    }
}