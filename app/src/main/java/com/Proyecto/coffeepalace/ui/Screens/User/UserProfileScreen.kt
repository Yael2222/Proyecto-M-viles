package com.Proyecto.coffeepalace.ui.Screens.User

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.Proyecto.coffeepalace.Data.Model.OrdenWithDetails
import com.Proyecto.coffeepalace.di.AppContainer
import com.Proyecto.coffeepalace.ui.components.AppBottomNavigationBar
import com.Proyecto.coffeepalace.ui.components.AppTopBar
import com.Proyecto.coffeepalace.ui.navigations.Screens
import com.Proyecto.coffeepalace.ui.theme.white
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(
        factory = UserViewModel.UserViewModelFactory(
            AppContainer.authRepository,
            AppContainer.userRepository,
            AppContainer.orderRepository
        )
    )
) {
    val isLoggedIn by userViewModel.isLoggedIn.collectAsState()
    val userEmail by userViewModel.userEmail.collectAsState()
    val userName by userViewModel.userName.collectAsState()
    val userImage by userViewModel.userImage.collectAsState()
    val logoutMessage by userViewModel.logoutMessage.collectAsState()
    val showSnackbar by userViewModel.showSnackbar.collectAsState()
    val userOrders by userViewModel.userOrders.collectAsState()
    val isLoadingOrders by userViewModel.isLoadingOrders.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            userViewModel.uploadProfileImage(it)
        }
    }

    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            navController.navigate(Screens.Login.route) {
                popUpTo(Screens.User_Settings.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        } else {
            userViewModel.loadUserData()
            userViewModel.loadUserProfileImage()
            userViewModel.loadUserOrders()
        }
    }

    LaunchedEffect(logoutMessage, showSnackbar) {
        logoutMessage?.let { message ->
            snackbarHostState.showSnackbar(message = message, duration = SnackbarDuration.Short)
            userViewModel.clearLogoutMessage()
        }
        showSnackbar?.let { (message, isError) ->
            snackbarHostState.showSnackbar(message = message, duration = SnackbarDuration.Short)
            userViewModel.clearSnackbarMessage()
        }
    }

    Scaffold(
        topBar = { AppTopBar(navController = navController, userViewModel = userViewModel) },
        bottomBar = { AppBottomNavigationBar(navController) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        if (isLoggedIn) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    Spacer(modifier = Modifier.height(32.dp))

                    Surface(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .clickable { pickImageLauncher.launch("image/*") },
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    ) {
                        if (!userImage.isNullOrEmpty()) {
                            AsyncImage(
                                model = userImage,
                                contentDescription = "Foto de perfil de usuario",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                Icons.Filled.Person,
                                contentDescription = "Foto de perfil por defecto",
                                modifier = Modifier.fillMaxSize(0.6f),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    userName?.let { name ->
                        Text(
                            text = name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    } ?: Text(
                        text = "Usuario",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    userEmail?.let { email ->
                        Text(
                            text = email,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Divider(modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { userViewModel.logout() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "Cerrar Sesión")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cerrar Sesión" , color = white)
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "Mis Pedidos",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (isLoadingOrders) {
                    item {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                } else if (userOrders.isEmpty()) {
                    item {
                        Text("No tienes pedidos realizados aún.", style = MaterialTheme.typography.bodyLarge)
                    }
                } else {
                    items(userOrders) { order ->
                        OrderItemCard(order = order)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun OrderItemCard(order: OrdenWithDetails) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Pedido #${order.factura?.numeroFactura ?: "N/A"}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))

            val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val dateText = order.factura?.fecha?.let { dateFormatter.format(it) } ?: "N/A"
            Text(
                text = "Fecha: $dateText",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Estado: ${order.estado.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}",
                style = MaterialTheme.typography.bodyMedium,
                color = if (order.estado.equals("listo", ignoreCase = true)) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Se elimina la sección de "Productos" ya que no es necesaria mostrarla
            // if (!order.factura?.detallesFactura.isNullOrEmpty()) {
            //     Text(
            //         text = "Productos:",
            //         style = MaterialTheme.typography.titleSmall,
            //         fontWeight = FontWeight.SemiBold
            //     )
            //     // ... (toda la lógica de agrupación y display de productos eliminada) ...
            // } else {
            //     Text("No hay detalles de productos para esta orden.", style = MaterialTheme.typography.bodySmall)
            // }

            // Se mantiene el divisor y el total si la factura no es nula
            order.factura?.let {
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$${String.format("%.2f", it.totalFactura)}", // Usar 'it.totalFactura' directamente
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            } ?: run {
                // Opcional: Mostrar un mensaje si la factura es nula por alguna razón
                Text("Detalles de la factura no disponibles.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}