package com.Proyecto.coffeepalace.ui.Screens.Customer.Home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.Proyecto.coffeepalace.ui.components.BottomBar
import com.Proyecto.coffeepalace.ui.navigations.NavigationRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Coffee Palace") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF8A4F2E),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomBar(
                currentRoute = currentRoute ?: "",
                onHomeClick = { /* Ya estamos aquí */ },
                onCategoryClick = { navController.navigate(NavigationRoutes.CATEGORY) },
                onSearchClick = { navController.navigate(NavigationRoutes.SEARCH) },
                onProfileClick = { navController.navigate(NavigationRoutes.PROFILE) },
                onCartClick = { navController.navigate(NavigationRoutes.CART) }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Bienvenido a Coffee Palace",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate(NavigationRoutes.SEARCH) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8A4F2E)
                    )
                ) {
                    Text("Ir a Búsqueda")
                }
            }
        }
    }
}
