package com.Proyecto.coffeepalace.ui.Screens.Client.ShoppingCart

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.Proyecto.coffeepalace.Data.Model.CartItem
import com.Proyecto.coffeepalace.di.AppContainer
import com.Proyecto.coffeepalace.di.AppContainer.userViewModelFactory // Keep this import if you still use it for factory access
import com.Proyecto.coffeepalace.ui.Screens.User.UserViewModel // Importa el UserViewModel
import com.Proyecto.coffeepalace.ui.components.AppBottomNavigationBar // Importa el bottom bar
import com.Proyecto.coffeepalace.ui.components.AppTopBar // Importa el top bar
import com.Proyecto.coffeepalace.ui.navigations.Screens // Si usas Screens para rutas

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(
        factory = AppContainer.userViewModelFactory // Corrected: Access factory via AppContainer
    ),
    shoppingCartViewModel: ShoppingCartViewModel = viewModel(
        factory = ShoppingCartViewModelFactory(
            AppContainer.shoppingCartRepository,
            AppContainer.authRepository,
            AppContainer.orderRepository
        )
    )
) {
    val cartItems by shoppingCartViewModel.cartItems.collectAsState()
    val isLoading by shoppingCartViewModel.isLoading.collectAsState()
    val error by shoppingCartViewModel.error.collectAsState()
    val totalPrice by shoppingCartViewModel.totalPrice.collectAsState()
    val checkoutSuccess by shoppingCartViewModel.checkoutSuccess.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val showDialog = remember { mutableStateOf(false) }

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(it)
            shoppingCartViewModel.clearError()
        }
    }

    LaunchedEffect(checkoutSuccess) {
        if (checkoutSuccess) {
            snackbarHostState.showSnackbar("¡Compra realizada con éxito!")
            shoppingCartViewModel.clearCheckoutSuccess()
            navController.navigate(Screens.User_Settings.route) {
                popUpTo(Screens.ShoppingCart.route) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(navController = navController, userViewModel = userViewModel)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            Column { // Use a Column to stack your bottom bars
                if (cartItems.isNotEmpty() && !isLoading) {
                    CartBottomBar(
                        totalPrice = totalPrice,
                        onCheckoutClicked = { showDialog.value = true }
                    )
                }
                // Always show the AppBottomNavigationBar
                AppBottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValues ->
        // The content area now correctly accounts for both top and bottom bars
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // This padding accounts for both top and bottom bars
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (cartItems.isEmpty()) {
                Text(
                    text = "Tu carrito está vacío.",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.headlineSmall
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(cartItems) { item ->
                        CartItemCard(
                            cartItem = item,
                            onIncreaseQuantity = { shoppingCartViewModel.increaseQuantity(it) },
                            onDecreaseQuantity = { shoppingCartViewModel.decreaseQuantity(it) },
                            onRemoveItem = { shoppingCartViewModel.removeAllUnitsOfProduct(it) }
                        )
                    }
                }
            }
        }

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text("Confirmar Compra") },
                text = {
                    Text("El pago se realizará en efectivo al retirar su pedido en el establecimiento.")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog.value = false
                            shoppingCartViewModel.checkout()
                        }
                    ) {
                        Text("Confirmar Compra")
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = { showDialog.value = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onIncreaseQuantity: (CartItem) -> Unit,
    onDecreaseQuantity: (CartItem) -> Unit,
    onRemoveItem: (CartItem) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = cartItem.productImageUrl),
                contentDescription = cartItem.productName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cartItem.productName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${String.format("%.2f", cartItem.productPrice)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = { onDecreaseQuantity(cartItem) },
                        modifier = Modifier.size(32.dp),
                        enabled = cartItem.quantity > 0
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Disminuir Cantidad")
                    }
                    Text(
                        text = "${cartItem.quantity}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = { onIncreaseQuantity(cartItem) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Aumentar Cantidad")
                    }
                }
            }
            IconButton(
                onClick = { onRemoveItem(cartItem) },
                modifier = Modifier.align(Alignment.Top)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar Producto", tint = Color.Red)
            }
        }
    }
}

@Composable
fun CartBottomBar(totalPrice: Double, onCheckoutClicked: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        color = MaterialTheme.colorScheme.primary,
        shadowElevation = 8.dp // Added shadow back for consistency, but not strictly needed to fix the gap
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Total:",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$${String.format("%.2f", totalPrice)}",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = onCheckoutClicked,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary)
            ) {
                Text("Comprar", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}