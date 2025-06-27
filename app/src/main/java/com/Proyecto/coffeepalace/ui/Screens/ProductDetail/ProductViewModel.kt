package com.Proyecto.coffeepalace.ui.Screens.ProductDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.Comment
import com.Proyecto.coffeepalace.Data.Repository.CommentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: CommentRepository) : ViewModel() {
    var newCommentText = MutableStateFlow("")
    var newRating = MutableStateFlow(0)

    val comments: StateFlow<List<Comment>> = repository.getAllComments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addComment() {
        val text = newCommentText.value
        val rating = newRating.value
        if (text.isNotBlank() && rating > 0) {
            viewModelScope.launch {
                repository.insertComment(Comment(text = text, rating = rating))
                newCommentText.value = ""
                newRating.value = 0
            }
        }
    }
}

