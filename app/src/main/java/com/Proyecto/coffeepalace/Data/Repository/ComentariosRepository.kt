package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Model.AddComentarioRequest
import com.Proyecto.coffeepalace.Data.Model.comentario_producto
import com.Proyecto.coffeepalace.Data.Network.ApiService
class ComentariosRepository(
    private val comentariosApiService: ApiService
) {
    suspend fun getComentariosByProductoId(idProducto: Int): Result<List<comentario_producto>> {
        return try {
            val comentarios = comentariosApiService.getComentariosByProductoId(idProducto)
            Result.Success(comentarios)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun addComentario(userId: String, idProducto: Int, texto: String, calificacion: Int): Result<Unit> {
        return try {
            val request = AddComentarioRequest(id_producto = idProducto, texto = texto, calificacion = calificacion)
            comentariosApiService.addComentario(request)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}