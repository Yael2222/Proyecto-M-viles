package com.Proyecto.coffeepalace.Data.Database.Dao

import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.Proyecto.coffeepalace.Data.Model.Comment

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments")
    fun getAllComments(): Flow<List<Comment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: Comment)

    @Query("DELETE FROM comments")
    suspend fun deleteAll()
}
