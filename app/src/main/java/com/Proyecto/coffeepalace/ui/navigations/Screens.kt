package com.Proyecto.coffeepalace.ui.navigations

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Proyecto.coffeepalace.ui.Screens.Seller.Category.CategoryScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.Category.CategoryViewModel
import com.Proyecto.coffeepalace.ui.Screens.Seller.HomeSeller.HomeSellerScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.HomeSeller.HomeSellerViewModel

sealed class Screens(val route: String) {
    object HomeSeller : Screens("home_seller")
    object AddProduct : Screens("add_product")
    object ViewStock : Screens("view_stock")
    object ViewUsers : Screens("view_users")
    object Estadistics : Screens("estadistics")
    object Comments : Screens("comments")
    object Category : Screens("category")
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.HomeSeller.route) {

        composable(route = Screens.HomeSeller.route) {
            val homeSellerViewModel: HomeSellerViewModel = viewModel()
            HomeSellerScreen(viewModel = homeSellerViewModel, navController = navController)
        }

        composable(route = Screens.Category.route) {
            val categoryViewModel: CategoryViewModel = viewModel()
            CategoryScreen(viewModel = categoryViewModel, navController = navController)
        }

        // Agrega aquí los otros destinos si los necesitas
    }
}
