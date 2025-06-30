package com.Proyecto.coffeepalace.Data.Daos.Comentario

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.Proyecto.coffeepalace.Data.Entity.ComentarioRecetaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ComentarioRecetaDao {
    @Query("SELECT * FROM comentarios_receta WHERE idReceta = :idReceta ORDER BY timestamp DESC")
    fun getComentariosByReceta(idReceta: Int): Flow<List<ComentarioRecetaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComentario(comentario: ComentarioRecetaEntity)

    @Delete
    suspend fun deleteComentario(comentario: ComentarioRecetaEntity)

    @Query("DELETE FROM comentarios_receta WHERE idReceta = :idReceta")
    suspend fun deleteComentariosByReceta(idReceta: Int)
}
