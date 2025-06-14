package com.Proyecto.coffeepalace.Data.Database.Dao

import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.Proyecto.coffeepalace.Data.Database.Entities.CommentEntity

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments WHERE recipeId = :recipeId")
    suspend fun getCommentsForRecipe(recipeId: Int): Flow<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentEntity)

    @Delete
    suspend fun delete(comment: CommentEntity)
}
