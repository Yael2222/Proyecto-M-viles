package com.Proyecto.coffeepalace.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.Proyecto.coffeepalace.ui.navigations.Screens
import com.Proyecto.coffeepalace.ui.Screens.User.UserViewModel // Asegúrate de importar tu UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    navController: NavController,
    userViewModel: UserViewModel // <-- ¡Nuevo parámetro!
) {
    // Observa la URL de la imagen de perfil del ViewModel
    val userProfileImageUrl by userViewModel.userImage.collectAsState()

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = "file:///android_asset/CooffeLogo2.png",
                    contentDescription = "Coffee Palace Logo",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(start = 16.dp, end = 8.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    "Coffee Palace",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFFFFFFFF),
                    modifier = Modifier.weight(1f)
                )
            }
        },
        actions = {
            IconButton(onClick = { navController.navigate(Screens.User_Settings.route) }) {
                // Lógica condicional para mostrar la imagen o el icono
                if (!userProfileImageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = userProfileImageUrl,
                        contentDescription = "Foto de Perfil del Usuario",
                        modifier = Modifier
                            .size(36.dp) // Tamaño adecuado para el icono en el Top Bar
                            .clip(CircleShape) // Recorta la imagen en círculo
                            .background(Color.Gray), // Un color de fondo por si la imagen tarda en cargar
                        contentScale = ContentScale.Crop // Escala para cubrir el espacio
                    )
                } else {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = "Configuración de Usuario",
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF492B2B), // marrón oscuro café
            titleContentColor = Color.White // si usas el título estándar
        )
    )
}

@Composable
fun AppBottomNavigationBar(navController: NavController) {
    val currentRoute = rememberSaveable { mutableStateOf(Screens.Home.route) }

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            currentRoute.value = backStackEntry.destination.route ?: Screens.Home.route
        }
    }

    NavigationBar(
        containerColor = Color(0xFF492B2B), // fondo marrón oscuro
        contentColor = Color.White          // íconos blancos por defecto
    ) {
        val navItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,         // icono seleccionado blanco
            unselectedIconColor = Color.White,       // icono no seleccionado blanco
            indicatorColor = Color.White.copy(alpha = 0.2f) // fondo del ítem seleccionado
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            selected = currentRoute.value == Screens.Home.route,
            onClick = {
                navController.navigate(Screens.Home.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = navItemColors
        )


        NavigationBarItem(
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito") },
            selected = currentRoute.value == Screens.ShoppingCart.route,
            onClick = {
                navController.navigate(Screens.ShoppingCart.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = navItemColors
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
            selected = currentRoute.value == Screens.Search.route,
            onClick = {
                navController.navigate(Screens.Search.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = navItemColors
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            selected = currentRoute.value == Screens.User_Settings.route,
            onClick = {
                navController.navigate(Screens.User_Settings.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = navItemColors
        )
    }
}