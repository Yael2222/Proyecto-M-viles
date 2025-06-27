/*package com.Proyecto.coffeepalace.Data.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//import com.Proyecto.coffeepalace.Data.Database.Dao.CommentDao
//import com.Proyecto.coffeepalace.Data.Database.Dao.RecipeDao
import com.Proyecto.coffeepalace.Data.Database.Dao.UserDao
import com.Proyecto.coffeepalace.Data.Database.Entities.*

@Database(
    entities = [UserEntity::class/*, RecipeEntity::class, CommentEntity::class*/], version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    //abstract fun recipeDao(): RecipeDao
    //abstract fun commentDao(): CommentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "coffee_palace_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
*/

import androidx.room.Database
import androidx.room.RoomDatabase
import com.Proyecto.coffeepalace.Data.Database.Dao.CommentDao
import com.Proyecto.coffeepalace.Data.Model.Comment

@Database(entities = [Comment::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun commentDao() : CommentDao
}
