package com.Proyecto.coffeepalace.ui.Screens.Client.PlaceOrderDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.ui.components.OrderDetailsItem
import com.Proyecto.coffeepalace.ui.components.RowItemDescription
import com.Proyecto.coffeepalace.ui.theme.LightBrown
import com.Proyecto.coffeepalace.ui.theme.LightGray200
import com.Proyecto.coffeepalace.ui.theme.LightGray400

@Composable
fun PlaceOrderDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaceOrderDetailsViewModel,
    productId: Long?
) {
    LaunchedEffect(productId) {
        if (productId != null)
            viewModel.getOwnShoppingCarById(productId)
    }
    val carAndProduct by viewModel.orderDetails.collectAsState()
    Column(
        modifier = modifier
            .background(LightGray200)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (carAndProduct != null)
            OrderDetailsItem(
                productName = carAndProduct?.producto?.nombre ?: "Devil Eggs",
                imageRes = carAndProduct?.producto?.imagen
                    ?: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s",
                initialQuantity = 1,
                orderDate = "2023-10-01",
                onQuantityChange = { },
            )
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                "Order Payment Details",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            RowItemDescription(
                title = "Order Amounts",
                description = String.format("%.2f", carAndProduct?.producto?.precio ?: 0.0),
                descriptionColor = Color.Black,
                descriptionFontWeight = FontWeight.Bold,
                textStyle = MaterialTheme.typography.bodySmall,
                titleColor = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            RowItemDescription(
                title = "Delivery Fee",
                titleColor = Color.Black,
                description = "Free",
                descriptionColor = LightBrown,
                textStyle = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp), color = LightGray400, thickness = 1.dp
        )
        Spacer(modifier = Modifier.height(34.dp))
        RowItemDescription(
            title = "Order Total",
            titleColor = Color.Black,
            description = String.format("%.2f", carAndProduct?.producto?.precio ?: 0.0),
            descriptionColor = Color.Black,
            descriptionFontWeight = FontWeight.Bold,
            textStyle = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(34.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp), color = LightGray400, thickness = 1.dp
        )
    }
}

