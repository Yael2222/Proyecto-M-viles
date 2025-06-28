package com.Proyecto.coffeepalace.Data.Model
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class receta (
    var id: Long? = null, // ¡IMPORTANTE: Hacer el ID nullable para nuevas instancias!
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("descripcion")
    val descripcion: String,
    @SerializedName("imagen")
    val imagen: String, // Asumimos que será una URL pública después de la subida
    @SerializedName("instrucciones")
    val instrucciones: String,
)