package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Daos.Comentario.ComentarioProductoDao
import com.Proyecto.coffeepalace.Data.Model.ComentarioProducto
import com.Proyecto.coffeepalace.Data.Remote.SupabaseClient
import com.Proyecto.coffeepalace.Data.Remote.dto.ComentarioProductoDto
import com.Proyecto.coffeepalace.Data.mapper.toModel
import com.Proyecto.coffeepalace.Data.mapper.toEntity
import com.Proyecto.coffeepalace.Data.Entity.ComentarioProductoEntity
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ComentarioProductoRepository(
    private val dao: ComentarioProductoDao
) {
    private val client = SupabaseClient.client

    fun getComentariosByProducto(idProducto: Int): Flow<List<ComentarioProducto>> {
        return dao.getComentariosByProducto(idProducto).map { entities ->
            entities.map { it.toModel() }
        }
    }

    suspend fun insertComentario(comentario: ComentarioProducto) {
        try {
            val dto = ComentarioProductoDto(
                id = comentario.id,
                id_producto = comentario.idProducto,
                id_usuario = comentario.idUsuario,
                texto = comentario.text,
                calificacion = comentario.rating
            )
            client.from("comentario_producto").insert(dto)

            dao.insertComentario(comentario.toEntity())
        } catch (e: Exception) {
            println("❌ Error al insertar en Supabase: ${e.message}")
            dao.insertComentario(comentario.toEntity())
        }
    }

    suspend fun syncComentarios(idProducto: Int) {
        try {
            val response = client.from("comentario_producto")
                .select {
                    filter { eq("id_producto", idProducto) }
                }
                .decodeList<ComentarioProductoDto>()

            dao.deleteComentariosByProducto(idProducto)

            response.forEach { dto ->
                val entity = ComentarioProductoEntity(
                    id = dto.id,
                    idProducto = dto.id_producto,
                    idUsuario = dto.id_usuario,
                    text = dto.texto,
                    rating = dto.calificacion
                )
                dao.insertComentario(entity)
            }
        } catch (e: Exception) {
            println("❌ Error al sincronizar comentarios: ${e.message}")
        }
    }
}
