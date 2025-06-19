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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
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
) {
    LazyVerticalGrid (
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .background(LightGray)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item (
            span = { GridItemSpan(2)}
        ){
            HomeTitle("Ice Coffee", modifier = Modifier.fillMaxWidth().padding(start = 16.dp))
            Spacer(Modifier.height(5.dp))
        }

        items(15) { index ->
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