package com.Proyecto.coffeepalace.ui.Screens.Seller.ViewStock

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.ui.theme.BrownCoffee


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewStockScreen(
    onOpenInventory: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ViewStockViewModel
) {
    val searchQuery by viewModel.searchQuery
    val filteredItems = viewModel.filterItems()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("View Stock") },
            navigationIcon = {
                IconButton(onClick = { /* volver */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.searchQuery.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = { Text("Search product...") }
        )

        LazyColumn {
            items(filteredItems) { product ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(product.name)
                            Text("${product.quantity} unidades", color = Color.Gray)
                        }
                        Button(onClick = {
                            onOpenInventory(product.name, product.quantity)
                        },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BrownCoffee,
                                contentColor = Color.White
                            )) {
                            Text("Actualizar")
                        }
                    }
                }
            }
        }
    }
}
