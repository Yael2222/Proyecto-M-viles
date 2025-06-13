/*package com.Proyecto.coffeepalace.ui.Screens.Seller.Comments

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Comment(
    val author: String,
    val text: String,
    val date: String,
    val rating: Int
)

data class ProductWithComments(
    val name: String,
    val price: String,
    val comments: List<Comment>
)

class CommentsViewModel : ViewModel() {
    private val _products = mutableStateListOf(
        ProductWithComments(
            name = "Iced Coffee",
            price = "$5.75",
            comments = listOf(
                Comment("María García", "¡El mejor café que he probado!", "24 abril, 2025", 5),
                Comment("Javier Ruíz", "No está mal, pero he probado mejores.", "25 abril, 2025", 4)
            )
        ),
        ProductWithComments(
            name = "Croissant",
            price = "$8.99",
            comments = listOf(
                Comment("Luis Morales", "Muy fresco y crujiente", "26 abril, 2025", 5)
            )
        )
    )
    val products: List<ProductWithComments> = _products
}
*/