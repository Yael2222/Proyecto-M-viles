package com.Proyecto.coffeepalace.Data.Model

data class comentario_producto(
    val id: Int,
    val id_usuario: Int,
    val id_producto: Int,
    val texto: String,
    val calificacion: Int,
    val usuario: ComentarioUsuarioDetail
)
data class ComentarioUsuarioDetail(
    val nombre: String,
    val imagen: String?
)