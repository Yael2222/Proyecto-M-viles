package com.Proyecto.coffeepalace.Data.Model

import com.google.gson.annotations.SerializedName // Asegúrate de tener esta importación
import kotlinx.serialization.Serializable

@Serializable
data class receta_ingrediente (
    val id: Long? = null, // ¡ID nullable para nuevas instancias!
    @SerializedName("id_ingrediente") // Asegúrate que coincide con el nombre de la columna en Supabase
    val id_ingrediente: Long,
    @SerializedName("id_receta") // Asegúrate que coincide con el nombre de la columna en Supabase
    val id_receta: Long
)