package com.Proyecto.coffeepalace.ui.Screens.Seller.Ordenes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.Proyecto.coffeepalace.Data.Model.OrdenWithDetails
import com.Proyecto.coffeepalace.Data.Model.Producto
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    val orders by viewModel.orders.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                viewModel.clearErrorMessage()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Órdenes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                orders.isEmpty() && !isLoading && errorMessage == null -> {
                    Text(
                        text = "No hay órdenes pendientes.",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(orders, key = { it.id ?: it.idFactura }) { order ->
                            OrderCard(
                                order = order,
                                onUpdateStatusClick = { orderId, newStatus ->
                                    viewModel.updateOrderStatus(orderId, newStatus)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: OrdenWithDetails, onUpdateStatusClick: (Long, String) -> Unit) {
    val factura = order.factura
    val detalles = factura?.detallesFactura ?: emptyList()
    val isReady = order.estado == "listo"

    // Obtener el nombre y correo del usuario si la factura y el usuario anidado existen
    val userName = factura?.usuario?.nombre ?: "Usuario Desconocido"
    val userEmail = factura?.usuario?.correo ?: "Correo Desconocido"

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isReady) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Información principal de la Orden y Factura
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Orden #${order.id ?: "N/A"}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Estado: ${order.estado.capitalize()}", // capitalize() es de kotlin.text, asegúrate de tenerlo
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isReady) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            factura?.let {
                Text(
                    text = "Factura #${it.numeroFactura} - Total: $${String.format("%.2f", it.totalFactura)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Fecha: ${it.fecha.toLocaleString()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                // <--- ¡CAMBIOS AQUÍ! Mostrar nombre y correo en lugar de ID
                Text(
                    text = "Cliente: $userName",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Correo: $userEmail",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Lista de Productos en la Factura
            Text(
                text = "Productos:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))

            if (detalles.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    detalles.forEach { detalle ->
                        detalle.producto?.let { prod ->
                            ProductOrderItem(product = prod)
                        } ?: Text("Producto desconocido (ID: ${detalle.idProducto})")
                    }
                }
            } else {
                Text(
                    text = "No hay productos en esta factura.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón para cambiar estado
            if (!isReady) {
                Button(
                    onClick = { order.id?.let { onUpdateStatusClick(it, "listo") } },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Marcar como Listo", color = MaterialTheme.colorScheme.onPrimary)
                }
            } else {
                Text(
                    text = "Esta orden ya está lista.",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
    }
}

@Composable
fun ProductOrderItem(product: Producto) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (!product.imagen.isNullOrBlank()) {
            Image(
                painter = rememberAsyncImagePainter(model = product.imagen),
                contentDescription = "Imagen de ${product.nombre}",
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = product.nombre,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Precio: $${String.format("%.2f", product.precio)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}