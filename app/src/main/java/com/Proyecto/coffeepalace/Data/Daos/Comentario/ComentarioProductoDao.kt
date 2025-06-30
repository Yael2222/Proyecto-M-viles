package com.Proyecto.coffeepalace.Data.Daos.Comentario

import androidx.room.*
import com.Proyecto.coffeepalace.Data.Entity.ComentarioProductoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ComentarioProductoDao {
    @Query("SELECT * FROM comentarios_producto WHERE idProducto = :idProducto ORDER BY timestamp DESC")
    fun getComentariosByProducto(idProducto: Int): Flow<List<ComentarioProductoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComentario(comentario: ComentarioProductoEntity)

    @Delete
    suspend fun deleteComentario(comentario: ComentarioProductoEntity)

    @Query("DELETE FROM comentarios_producto WHERE idProducto = :idProducto")
    suspend fun deleteComentariosByProducto(idProducto: Int)
}