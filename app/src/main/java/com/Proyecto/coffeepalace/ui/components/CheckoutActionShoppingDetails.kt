package com.Proyecto.coffeepalace.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.ui.theme.LightGray200
import com.Proyecto.coffeepalace.ui.theme.LightGray300

@Composable
fun CheckoutActionShoppingDetails(
    price: String,
    onViewDetailsClick: () -> Unit,
    onProceedToPaymentClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .border(
                1.dp, LightGray300,
                shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
            )
            .background(LightGray200, shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
            .padding(horizontal = 25.dp, vertical = 8.dp)
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = price,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color.Black
        )

        Spacer(modifier = Modifier.weight(1f))

        CustomButton(
            text = "Proceed to Payment",
            onClick = onProceedToPaymentClick,
            textColor = Color.White,
        )
    }
}