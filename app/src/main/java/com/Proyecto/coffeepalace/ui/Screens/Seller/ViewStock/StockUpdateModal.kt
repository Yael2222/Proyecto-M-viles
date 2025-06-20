package com.Proyecto.coffeepalace.ui.Screens.Seller.ViewStock

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.Proyecto.coffeepalace.ui.theme.BrownCoffee

@Composable
fun StockyUpdateModal(
    productName: String,
    currentStock: Int,
    onSave: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var quantity by remember { mutableStateOf(currentStock) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = { onSave(quantity) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrownCoffee,
                    contentColor = Color.White)) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrownCoffee,
                    contentColor = Color.White)) {
                Text("Cancel")
            }
        },
        title = { Text("Actualizar Inventario") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Ajusta la cantidad de $productName en inventario.")
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { if (quantity > 0) quantity-- }) {
                        Icon(Icons.Default.Remove, contentDescription = null)
                    }
                    Text("$quantity unidades", fontWeight = FontWeight.Bold)
                    IconButton(onClick = { quantity++ }) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }
                Text("Stock mínimo recomendado: 100 unidades", fontSize = 12.sp)
            }
        }
    )
}
