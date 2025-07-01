package com.Proyecto.coffeepalace.ui.navigations

import androidx.compose.material3.Text // Para los placeholders
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.Proyecto.coffeepalace.ui.Screens.AdminDashboard.AdminDashboardScreen
import com.Proyecto.coffeepalace.ui.Screens.AdminCategoryPage.CategoryScreen
import com.Proyecto.coffeepalace.ui.Screens.AdminAdsPage.AdminAdsScreen
import com.Proyecto.coffeepalace.ui.Screens.AdminUsersPage.ViewUsersScreen
import com.Proyecto.coffeepalace.ui.Screens.AdminCommentsPage.ViewCommentsWithUsersScreen
import com.Proyecto.coffeepalace.ui.Screens.AdminPOPage.ViewFacturasScreen
import com.Proyecto.coffeepalace.navigation.Screens

@Composable
fun MainNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screens.AdminDashboard.route, // Siempre comenzamos en el Dashboard
        modifier = modifier
    ) {
        // --- Pantalla de Admin Dashboard ---
        composable(Screens.AdminDashboard.route) {
            AdminDashboardScreen(
                onItemClick = { route ->
                    // Navegamos directamente a la ruta que nos da el DashboardItem
                    if (route == Screens.Login.route) {
                        // Para logout, navegamos a la pantalla de login y limpiamos la pila de navegación
                        navController.navigate(Screens.Login.route) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true } // Limpia la pila
                            launchSingleTop = true // Evita duplicados si la pantalla de login ya está en la pila
                        }
                    } else {
                        // Para cualquier otra ruta, simplemente navegamos
                        navController.navigate(route)
                    }
                }
            )
        }

        // --- Pantalla de Categorías ---
        composable(Screens.Categories.route) {
            CategoryScreen(
                onBackClick = { navController.popBackStack() } // Vuelve a la pantalla anterior (Dashboard)
                // onAddCategoryClick ya no es un parámetro aquí, se maneja internamente en CategoryScreen
            )
        }

        composable(Screens.Ads.route) {
            AdminAdsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // --- Pantalla de Login (Si la implementas) ---
        composable(Screens.Login.route) {
            Text(text = "Pantalla de Login (Pendiente)") // Placeholder
            // Aquí iría tu LoginScreen()
        }

        composable(Screens.Users.route) {
            ViewUsersScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screens.CommentsRatings.route) {
            ViewCommentsWithUsersScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screens.Orders.route) {
            ViewFacturasScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // --- Rutas Placeholder para otros items del Dashboard ---
        // Esto es para que no falle si haces clic en otros items del Dashboard que aún no tienen una pantalla real.
        // Las rutas de ejemplo en DashboardViewModel apuntan aquí temporalmente.
        composable("dashboard/statistics") { Text(text = "Estadísticas (Pendiente)") }
        composable("dashboard/complaints_suggestions") { Text(text = "Quejas y Sugerencias (Pendiente)") }
    }
}