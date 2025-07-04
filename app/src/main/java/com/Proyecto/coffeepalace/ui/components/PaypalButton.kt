package com.Proyecto.coffeepalace.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.Proyecto.coffeepalace.ui.Screens.Client.Checkout.PayPalManager
import com.paypal.android.paymentbuttons.PayPalButton

@Composable
fun PayPalButtonComponent(ppManager: PayPalManager, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            PayPalButton(context).apply {
                setOnClickListener { ppManager.startCheckout() }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    )
}