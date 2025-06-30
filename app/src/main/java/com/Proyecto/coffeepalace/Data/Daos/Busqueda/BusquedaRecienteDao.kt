package com.Proyecto.coffeepalace.Data.Daos.Busqueda

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.Proyecto.coffeepalace.Data.Model.BusquedaReciente

@Dao
interface BusquedaRecienteDao {
    @Query("SELECT * FROM BusquedaReciente ORDER BY id DESC LIMIT 10")
    suspend fun getRecientes(): List<BusquedaReciente>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(busqueda: BusquedaReciente)

    @Query("DELETE FROM BusquedaReciente")
    suspend fun limpiar()
}