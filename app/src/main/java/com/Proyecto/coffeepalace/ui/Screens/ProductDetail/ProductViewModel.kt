package com.Proyecto.coffeepalace.ui.Screens.ProductDetail

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.Proyecto.coffeepalace.Data.Model.Comment

class ProductViewModel : ViewModel() {
    var newCommentText = mutableStateOf("")
    var newRating = mutableStateOf(0)

    var comments = mutableStateListOf(
        Comment("Affogato - Neque porro quisquam est qui dolorem ipsum quia", 4),
        Comment("Affogato - Otro comentario de ejemplo", 5)
    )

    fun addComment() {
        if (newCommentText.value.isNotBlank() && newRating.value > 0) {
            comments.add(
                Comment(
                    text = "Affogato - ${newCommentText.value}",
                    rating = newRating.value
                )
            )
            newCommentText.value = ""
            newRating.value = 0
        }
    }
}
