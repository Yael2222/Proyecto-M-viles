package com.Proyecto.coffeepalace.ui.views.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.ui.views.components.Banner
import com.Proyecto.coffeepalace.ui.views.components.BannerCarousel
import com.Proyecto.coffeepalace.ui.views.components.HomeTitle
import com.Proyecto.coffeepalace.ui.views.components.ProductCard

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
) {
    LazyColumn {
        item {
            BannerCarousel(
                banners = listOf(
                    Banner("1", "Carrousel.png"),
                    Banner("2", "Carrousel.png"),
                    Banner("3", "Carrousel.png"),
                )
            )
        }
        items(10) { index ->
            HomeTitle("Ice Coffee", modifier = Modifier.padding(start = 16.dp))
            LazyRow {
                items(5) { index ->
                    Spacer(Modifier.width(5.dp))
                    ProductCard(
                        name = "Café Americano",
                        description = "Un café americano clásico y delicioso.",
                        price = "$2.50",
                        rating = 4.5f,
                        reviewCount = 1200,
                        imageRes = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s"
                    )
                }
            }
        }
    }
}