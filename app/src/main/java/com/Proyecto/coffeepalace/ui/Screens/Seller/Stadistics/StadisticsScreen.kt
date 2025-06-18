package com.Proyecto.coffeepalace.ui.Screens.Seller.Stadistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StadisticsScreen(viewModel: StadisticsViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        TopAppBar(
            title = { Text("Statistics") },
            navigationIcon = {
                IconButton(onClick = { /* Back */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )

        Text("Sales Statistics", style = MaterialTheme.typography.titleLarge)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("Total Sales")
                Text(viewModel.totalSales.value, style = MaterialTheme.typography.headlineSmall)
            }
            Column {
                Text("Orders")
                Text(viewModel.totalOrders.value, style = MaterialTheme.typography.headlineSmall)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Sales Trends")
        LineChart(data = viewModel.salesTrends)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Productos más vendidos", style = MaterialTheme.typography.titleMedium)
        viewModel.bestSellingProducts.forEach {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${it.name} - ${it.price}")
                Text("${it.unitsSold} unidades")
            }
        }
    }
}

@Composable
fun LineChart(data: List<Int>) {
    // Gráfico de ejemplo simple (solo como placeholder visual)
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
    ) {
        // Dibuja líneas de ejemplo
    }
}
