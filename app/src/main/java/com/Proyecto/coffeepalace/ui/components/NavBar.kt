package com.Proyecto.coffeepalace.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NavBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { /* TODO */ }) {
            Icon(Icons.Default.Add, contentDescription = "Add Product")
            Spacer(Modifier.width(24.dp))
            Text("Add product ")
        }
        Button(onClick = { /* TODO */ }) {
            Icon(Icons.Default.Inventory2, contentDescription = "View Stock")
            Spacer(Modifier.width(24.dp))
            Text("View Stock")
        }
        Button(onClick = { /* TODO */ }) {
            Icon(Icons.Default.Groups, contentDescription = "View Users")
            Spacer(Modifier.width(24.dp))
            Text("View Users")
        }
        Button(onClick = { /* TODO */ }) {
            Icon(Icons.Default.ShoppingCart, contentDescription = "Stadistics")
            Spacer(Modifier.width(24.dp))
            Text("Stadistics")
        }
        Button(onClick = { /* TODO */ }) {
            Icon(Icons.Default.Comment, contentDescription = "Comments")
            Spacer(Modifier.width(24.dp))
            Text("Comments")
        }
        Button(onClick = { /* TODO */ }) {
            Icon(Icons.Default.SignalCellularAlt, contentDescription = "Category")
            Spacer(Modifier.width(24.dp))
            Text("Category")
        }
    }
}
