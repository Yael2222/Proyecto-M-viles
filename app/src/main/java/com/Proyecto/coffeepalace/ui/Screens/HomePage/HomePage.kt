package com.Proyecto.coffeepalace.ui.Screens.HomePage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.Data.Model.Banner
import com.Proyecto.coffeepalace.ui.components.BannerCarousel
import com.Proyecto.coffeepalace.ui.components.FeaturesLazyRow
import com.Proyecto.coffeepalace.ui.components.HomeTitle
import com.Proyecto.coffeepalace.ui.components.ProductCard
import com.Proyecto.coffeepalace.ui.components.featureItems
import com.Proyecto.coffeepalace.ui.theme.LightGray

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.background(LightGray).padding(horizontal = 20.dp)) {
        item {
            FeaturesLazyRow(
                modifier = Modifier, featureItems
            )

        }
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