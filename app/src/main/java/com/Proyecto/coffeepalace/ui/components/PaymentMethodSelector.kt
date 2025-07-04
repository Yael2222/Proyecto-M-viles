package com.Proyecto.coffeepalace.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.ui.theme.LightGray400
import com.Proyecto.coffeepalace.ui.theme.gray

@Composable
fun PaymentMethodSelector(
    selectedMethod: String?,
    onMethodSelected: (String) -> Unit,
    paypalEmail: String = "**********2109",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.height(12.dp))

        PaymentOption(
            title = "Cash",
            icon = Icons.Default.Money,
            isSelected = selectedMethod == "Cash",
            onSelect = { onMethodSelected("Cash") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        PaymentOption(
            title = "Pick Up",
            icon = Icons.Default.Store,
            isSelected = selectedMethod == "Pick Up",
            onSelect = { onMethodSelected("Pick Up") }
        )
    }
}

@Composable
private fun PaymentOption(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onSelect: () -> Unit,
    subtitle: String? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) gray
            else LightGray400
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) Color.White
            else Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Color.White
                else Color.Black,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    ),
                    color = if (isSelected) Color.White
                    else Color.Black
                )

                subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            if (isSelected) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}