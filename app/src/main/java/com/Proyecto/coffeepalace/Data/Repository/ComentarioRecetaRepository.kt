package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Daos.Comentario.ComentarioRecetaDao
import com.Proyecto.coffeepalace.Data.Entity.ComentarioRecetaEntity
import com.Proyecto.coffeepalace.Data.Model.ComentarioReceta
import com.Proyecto.coffeepalace.Data.Remote.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

class ComentarioRecetaRepository(
    private val dao: ComentarioRecetaDao
) {
    private val client = SupabaseClient.client

    fun getComentariosByReceta(idReceta: Int): Flow<List<ComentarioReceta>> {
        return dao.getComentariosByReceta(idReceta).map { entities ->
            entities.map { entity ->
                ComentarioReceta(
                    id = entity.id,
                    idReceta = entity.idReceta,
                    idUsuario = entity.idUsuario,
                    text = entity.text,
                    rating = entity.rating,
                    timestamp = entity.timestamp
                )
            }
        }
    }

    suspend fun insertComentario(comentario: ComentarioReceta) {
        try {
            // Insertar en Supabase
            val dto = ComentarioRecetaDto(
                id_receta = comentario.idReceta,
                id_usuario = comentario.idUsuario,
                texto = comentario.text,
                calificacion = comentario.rating
            )

            client.from("comentario_receta").insert(dto)

            // Insertar en Room como respaldo
            val entity = ComentarioRecetaEntity(
                idReceta = comentario.idReceta,
                idUsuario = comentario.idUsuario,
                text = comentario.text,
                rating = comentario.rating,
                timestamp = comentario.timestamp
            )
            dao.insertComentario(entity)
        } catch (e: Exception) {
            // Si falla Supabase, al menos guardar en Room
            val entity = ComentarioRecetaEntity(
                idReceta = comentario.idReceta,
                idUsuario = comentario.idUsuario,
                text = comentario.text,
                rating = comentario.rating,
                timestamp = comentario.timestamp
            )
            dao.insertComentario(entity)
            println("Error inserting comment to Supabase: ${e.message}")
        }
    }

    suspend fun syncComentarios(idReceta: Int) {
        try {
            val response = client.from("comentario_receta")
                .select()
                .eq("id_receta", idReceta)
                .decodeList<ComentarioRecetaDto>()

            // Limpiar comentarios locales de la receta
            dao.deleteComentariosByReceta(idReceta)

            // Insertar comentarios de Supabase en Room
            response.forEach { dto ->
                val entity = ComentarioRecetaEntity(
                    id = dto.id,
                    idReceta = dto.id_receta,
                    idUsuario = dto.id_usuario,
                    text = dto.texto,
                    rating = dto.calificacion
                )
                dao.insertComentario(entity)
            }
        } catch (e: Exception) {
            println("Error syncing comments from Supabase: ${e.message}")
        }
    }
}
