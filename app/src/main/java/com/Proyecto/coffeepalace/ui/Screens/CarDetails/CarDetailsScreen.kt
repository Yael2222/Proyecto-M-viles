package com.Proyecto.coffeepalace.ui.Screens.CarDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.Data.Model.DeliveryAddressModel
import com.Proyecto.coffeepalace.R
import com.Proyecto.coffeepalace.ui.components.CheckoutActionShoppingDetails
import com.Proyecto.coffeepalace.ui.components.DeliveryAddressCard
import com.Proyecto.coffeepalace.ui.components.HomeTitle
import com.Proyecto.coffeepalace.ui.components.ShoppingListCardDetails
import com.Proyecto.coffeepalace.ui.theme.LightGray
import com.Proyecto.coffeepalace.ui.theme.LightGray200
import com.Proyecto.coffeepalace.ui.theme.LightGray300

@Composable
fun CarDetailsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(LightGray200)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn(
            modifier = Modifier
                .background(LightGray)
                .fillMaxWidth()
                .weight(0.85f)
                .padding(16.dp)
        ) {
            item {
                DeliveryAddressCard(
                    deliveryAddressModel = DeliveryAddressModel(
                        id = 0,
                        address = "216 St Paul's Rd London N1 2LL, UK",
                        contact = "+44 784232"
                    ), onEditClick = {})
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
                HomeTitle("Shopping List", fontStyle = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(10.dp))
            }
            items(3) {
                ShoppingListCardDetails(
                    itemName = "Deviled Eggs",
                    currentPrice = "$34.00",
                    discountText = "upto 33% off",
                    originalPrice = "$64.00",
                    imageRes = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiBqRLIZq2zTqKFNPt5wAmVzDiePmUnp0KvQ&s",
                    quantity = 1
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        CheckoutActionShoppingDetails(
            price = "10",
            onViewDetailsClick = {},
            onProceedToPaymentClick = {},
            modifier = Modifier.weight(0.15f)
        )
    }

}

