package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Model.Comment
import com.Proyecto.coffeepalace.Data.Database.Dao.CommentDao
import kotlinx.coroutines.flow.Flow

class CommentRepository(private val dao: CommentDao) {
    fun getAllComments(): Flow<List<Comment>> = dao.getAllComments()
    suspend fun insertComment(comment: Comment) = dao.insertComment(comment)
}
