package com.Proyecto.coffeepalace.ui.Screens.Checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.ui.components.CustomButton
import com.Proyecto.coffeepalace.ui.components.HomeTitle
import com.Proyecto.coffeepalace.ui.components.OrderSummary
import com.Proyecto.coffeepalace.ui.components.PaymentMethodSelector
import com.Proyecto.coffeepalace.ui.theme.LightGray200

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
) {
    var selectedMethod by remember { mutableStateOf<String?>(null) }
    LazyColumn(
        modifier = modifier
            .background(LightGray200)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            OrderSummary(
                orderAmount = "$4.99",
                shippingAmount = "$4.99",
                totalAmount = "$9.98",
                onPaymentClick = {}
            )
        }

        item {
            HomeTitle(title = "Payment", modifier = Modifier.padding(16.dp))
            PaymentMethodSelector(
                selectedMethod = selectedMethod,
                onMethodSelected = { method ->
                    selectedMethod = method
                },
                paypalEmail = "**********2109"
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            CustomButton(
                text = "Continue",
                onClick = { },
                modifier = Modifier.fillMaxWidth().padding(20.dp)
            )
        }
    }
}