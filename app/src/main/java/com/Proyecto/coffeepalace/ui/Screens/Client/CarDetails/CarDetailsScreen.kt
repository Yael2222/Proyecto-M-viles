package com.Proyecto.coffeepalace.ui.Screens.Client.CarDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.Data.Model.DeliveryAddressModel
import com.Proyecto.coffeepalace.ui.components.CheckoutActionShoppingDetails
import com.Proyecto.coffeepalace.ui.components.DeliveryAddressCard
import com.Proyecto.coffeepalace.ui.components.HomeTitle
import com.Proyecto.coffeepalace.ui.components.ShoppingListCardDetails
import com.Proyecto.coffeepalace.ui.theme.LightGray
import com.Proyecto.coffeepalace.ui.theme.LightGray200

@Composable
fun CarDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: CarDetailsViewModel,
    navigateToCheckout: () -> Unit = {},
    navigateToOrderDetails: (Long) -> Unit = {}
) {

    val shoppingCarProducts by viewModel.shoppingcarProducts.collectAsState()
    val user by viewModel.userInformation.collectAsState()
    val total by viewModel.total.collectAsState()

    LaunchedEffect(Unit) {
        snapshotFlow {
            shoppingCarProducts
        }.collect {
            viewModel.getTotalFromShoppingCar()
        }
    }

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
            if (user != null)
                item {
                    DeliveryAddressCard(
                        deliveryAddressModel = DeliveryAddressModel(
                            id = user?.id ?: 0,
//                            address = user?.address ?: "216 St Paul's Rd London N1 2LL, UK",
                            address = "216 St Paul's Rd London N1 2LL, UK",
                            contact = "78423234"
//                            contact = user?.cellphone ?: "78423234"
                        ), onEditClick = {})
                }
            item {
                Spacer(modifier = Modifier.height(10.dp))
                HomeTitle("Shopping List", fontStyle = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(10.dp))
            }
            if (shoppingCarProducts.isNotEmpty())
                items(shoppingCarProducts) { item ->
                    ShoppingListCardDetails(
                        itemName = item.producto.nombre,
                        currentPrice = String.format("%.2f", item.producto.precio),
                        discountText = "upto 33% off",
                        originalPrice = String.format("%.2f", item.producto.precio),
                        imageRes = item.producto.imagen,
                        quantity = 1,
//                        navigateToOrderDetails = {
//                            navigateToOrderDetails(item.producto.id)
//                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
        }
        CheckoutActionShoppingDetails(
            price = "$ ${String.format("%.2f", total)}",
            onViewDetailsClick = {},
            onProceedToPaymentClick = {
                navigateToCheckout()
            },
            modifier = Modifier.weight(0.15f)
        )
    }
}
