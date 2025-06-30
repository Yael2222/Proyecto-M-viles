package com.Proyecto.coffeepalace.ui.Screens.Client.HomePage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.Data.Model.Anuncio
import com.Proyecto.coffeepalace.ui.Screens.Client.HomeFiltered.FilteredTypes
import com.Proyecto.coffeepalace.ui.components.BannerCarousel
import com.Proyecto.coffeepalace.ui.components.FeaturesLazyRow
import com.Proyecto.coffeepalace.ui.components.HomeTitle
import com.Proyecto.coffeepalace.ui.components.ProductCard
import com.Proyecto.coffeepalace.ui.theme.LightGray
import kotlinx.serialization.Serializable

@Serializable
object HomePageRoute

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    homeViewModel: HomePageViewModel,
    navigateToHomeFiltered: (Long) -> Unit

) {
    val categories by homeViewModel.categories.collectAsState()
    val announcements by homeViewModel.announcements.collectAsState()
    val productsWithCategories by homeViewModel.productsWithCategories.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LightGray)
            .padding(horizontal = 20.dp)
    ) {

        if (categories.isNotEmpty())
            item {
                FeaturesLazyRow(
                    modifier = Modifier, categories, navigateToHomeFiltered
                )

            }
        if (announcements.isNotEmpty())
            item {
                BannerCarousel(
                    anuncios = announcements,
                )
            }
        if (productsWithCategories.isNotEmpty())
            items(productsWithCategories) { productsAndCategory ->
                HomeTitle(
                    title = productsAndCategory.category.nombre,
                    modifier = Modifier.padding(start = 16.dp)
                )
                if (productsAndCategory.products.isNotEmpty())
                    LazyRow {
                        items(productsAndCategory.products) { product ->
                            Spacer(Modifier.width(5.dp))
                            ProductCard(
                                name = product.nombre,
                                description = product.descripcion,
                                price = String.format("%.2f", product.precio),
                                rating = 4.5f,
                                reviewCount = 1200,
                                imageRes = product.imagen
                            )
                        }
                    }
            }
    }
}