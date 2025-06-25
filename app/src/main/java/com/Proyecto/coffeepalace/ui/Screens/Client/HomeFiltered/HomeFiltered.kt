package com.Proyecto.coffeepalace.ui.Screens.Client.HomeFiltered

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.ui.components.FeaturesLazyRow
import com.Proyecto.coffeepalace.ui.components.HomeTitle
import com.Proyecto.coffeepalace.ui.components.ProductCard
import com.Proyecto.coffeepalace.ui.theme.LightGray

@Composable
fun HomeFiltered(
    modifier: Modifier = Modifier,
    categoryId: Long? = null,
    viewModel: HomeFilteredViewModel
) {
    LaunchedEffect(categoryId) {
        if (categoryId != null)
            viewModel.loadProducts(categoryId)
    }

    val categoryAndProducts by viewModel.productsWithCategory.collectAsState()
    val allProducts by viewModel.allProducts.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .background(LightGray)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        if (categoryAndProducts != null)
            item(
                span = { GridItemSpan(2) }
            ) {
                HomeTitle(
                    categoryAndProducts!!.category.nombre, modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                )
                Spacer(Modifier.height(5.dp))
            }


        if (categoryAndProducts != null)
            items(categoryAndProducts!!.products) { product ->
                ProductCard(
                    name = product.nombre,
                    description = product.descripcion,
                    price = String.format("%.2f", product.precio),
                    rating = 4.5f,
                    reviewCount = 1200,
                    imageRes = product.imagen
                )

            }
        else if (allProducts.isNotEmpty())
            items(allProducts) { product ->
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