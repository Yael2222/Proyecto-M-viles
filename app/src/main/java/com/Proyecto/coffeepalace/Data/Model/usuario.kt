package com.Proyecto.coffeepalace.Data.Model

import com.google.gson.annotations.SerializedName // Asegúrate de tener esta importación
import kotlinx.serialization.Serializable

@Serializable
data class usuario (
    val id: Long? = null, // Supabase id (UUID o int8) debería ser Long en Kotlin. Hazlo nullable.
    @SerializedName("nombre")
    val nombre: String?, // Nombre nullable si puede ser null en DB
    @SerializedName("correo")
    val correo: String,
    @SerializedName("imagen")
    val imagen: String?, // Imagen (URL) nullable
    @SerializedName("rol")
    val rol: String,
    @SerializedName("auth_id") // Asegúrate que el nombre de la columna en Supabase es 'auth_id'
    val auth_id: String
)