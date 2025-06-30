package com.Proyecto.coffeepalace.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.Proyecto.coffeepalace.Data.Daos.Busqueda.BusquedaRecienteDao
import com.Proyecto.coffeepalace.Data.Daos.Comentario.ComentarioRecetaDao
import com.Proyecto.coffeepalace.Data.Entity.ComentarioProductoEntity
import com.Proyecto.coffeepalace.Data.Entity.ComentarioRecetaEntity
import com.Proyecto.coffeepalace.Data.Model.BusquedaReciente
import com.Proyecto.coffeepalace.Data.Daos.Comentario.ComentarioProductoDao

@Database(
    entities = [
        ComentarioProductoEntity::class,
        ComentarioRecetaEntity::class,
        BusquedaReciente::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun comentarioProductoDao(): ComentarioProductoDao
    abstract fun comentarioRecetaDao(): ComentarioRecetaDao
    abstract fun busquedaDao(): BusquedaRecienteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "coffee_palace_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}




/*import androidx.room.Database
import androidx.room.RoomDatabase
import com.Proyecto.coffeepalace.Data.Daos.Busqueda.BusquedaRecienteDao
import com.Proyecto.coffeepalace.Data.Model.BusquedaReciente

@Database(
    entities = [BusquedaReciente::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun busquedaDao(): BusquedaRecienteDao
}
 */


/*import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.Proyecto.coffeepalace.Data.Daos.Busqueda.DaoProducto
import com.Proyecto.coffeepalace.Data.Daos.Busqueda.DaoProductoImpl

@Database(entities = [DaoProductoImpl::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun busquedaDao(): DaoProducto

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "busquedas_db"
                ).build().also { INSTANCE = it }
            }
    }
}*/
