package com.Proyecto.coffeepalace.ui.Screens.Seller

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.Data.Dummy.Order
import com.Proyecto.coffeepalace.ui.components.NavBar


@Composable
fun HomeSellerScreen(
    viewModel: HomeSellerViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Welcome, Café Aroma!", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoCard(
                title = "Today's Sales",
                value = "\$${uiState.salesToday}",
                modifier = Modifier.weight(1f)
            )
            InfoCard(
                title = "Today's Orders",
                value = "${uiState.ordersToday}",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Pedidos actuales", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(uiState.currentOrders) { order ->
                OrderItem(order)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        NavBar()
    }
}

@Composable
fun InfoCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(4.dp)) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Text(value, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(order.customerName)
                Text(order.status, style = MaterialTheme.typography.bodySmall)
            }
            Text("\$${order.amount}")
        }
    }

}



