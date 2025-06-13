/*package com.Proyecto.coffeepalace.ui.Screens.Seller.Ratings

import androidx.lifecycle.ViewModel
import com.Proyecto.coffeepalace.ui.Screens.Seller.Comments.ProductWithComments

class RatingsViewModel(private val product: ProductWithComments) : ViewModel() {
        val comments = product.comments
        val productName = product.name
        val averageRating = comments.map { it.rating }.average()
        val totalRatings = comments.size
    }

*/