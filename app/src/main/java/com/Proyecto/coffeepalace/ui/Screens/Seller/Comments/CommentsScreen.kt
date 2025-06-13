/*package com.Proyecto.coffeepalace.ui.Screens.Seller.Comments

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.ui.Screens.Seller.Stadistics.ProductStats

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreen(viewModel: CommentsViewModel, onProductClick: (ProductWithComments) -> Unit) {
    val products = viewModel.products

    Column(modifier = Modifier.padding(16.dp)) {
        // ... AppBar + Search bar

        LazyColumn {
            items(products) { product ->
                Card( /* ... */ ) {
                    Row( /* ... */ ) {
                        Column {
                            Text(product.name)
                            Text(product.price)
                        }
                        Button(onClick = { onProductClick(product) }) {
                            Text("View")
                        }
                    }
                }
            }
        }
    }
}
*/