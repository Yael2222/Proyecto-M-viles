package com.Proyecto.coffeepalace.ui.Screens.Customer.Cart

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.Proyecto.coffeepalace.ui.navigations.NavigationRoutes
import com.Proyecto.coffeepalace.ui.components.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF8A4F2E),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomBar(
                currentRoute = currentRoute ?: "",
                onHomeClick = { navController.navigate(NavigationRoutes.HOME) },
                onCategoryClick = { navController.navigate(NavigationRoutes.CATEGORY) },
                onSearchClick = { navController.navigate(NavigationRoutes.SEARCH) },
                onProfileClick = { navController.navigate(NavigationRoutes.PROFILE) },
                onCartClick = { /* Ya estamos aquí */ }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Pantalla de Carrito",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
