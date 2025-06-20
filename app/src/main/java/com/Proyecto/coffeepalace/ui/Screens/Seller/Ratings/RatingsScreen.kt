/*package com.Proyecto.coffeepalace.ui.Screens.Seller.Ratings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.ui.Screens.Seller.Comments.ProductWithComments
import com.Proyecto.coffeepalace.ui.Screens.Seller.Stadistics.ProductStats


@Composable
fun RatingsScreen(product: ProductWithComments) {
    val comments = product.comments
    val average = comments.map { it.rating }.average()
    val total = comments.size

    Column(modifier = Modifier.padding(16.dp)) {
        
        Text(product.name, style = MaterialTheme.typography.headlineSmall)
        Text("★★★★☆ ${String.format("%.1f", average)} ($total ratings)")

        Text("Comentarios")
        comments.forEach {
            CommentCard(it.author, it.text, it.date, it.rating)
        }
    }
}
*/