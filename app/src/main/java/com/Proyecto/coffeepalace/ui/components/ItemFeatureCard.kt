package com.Proyecto.coffeepalace.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.Proyecto.coffeepalace.Data.Model.ItemFeatureModel
import com.Proyecto.coffeepalace.ui.theme.black

val featureItems = listOf(
    ItemFeatureModel(
        1,
        "Drinks",
        "Drinks",
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s"
    ),
    ItemFeatureModel(
        2,
        "Food",
        "Food",
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s"
    ),
    ItemFeatureModel(
        3,
        "At Home Coffee",
        "Coffee Shop",
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s"
    ),
    ItemFeatureModel(
        4,
        "Merchandise",
        "Dishes",
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s"
    ),
)

@Composable
fun FeaturesLazyRow(
    modifier: Modifier = Modifier,
    items: List<ItemFeatureModel> = featureItems
) {
    LazyRow(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .padding(vertical = 8.dp)
            .background(Color.White),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(items) { itemFeature ->
            FeatureItemCard(
                itemFeature = itemFeature,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun FeatureItemCard(
    itemFeature: ItemFeatureModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(150.dp)
            .width(110.dp),
        elevation = CardDefaults.elevatedCardElevation(
            0.dp
        ),
        colors = CardDefaults.cardColors(
            Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(itemFeature.image)
                    .crossfade(true)
                    .build(),
                contentDescription = "Product Image",
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(100.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.height(50.dp),
                text = itemFeature.title,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun ItemFeatureCardPreview() {
    FeaturesLazyRow()
}
