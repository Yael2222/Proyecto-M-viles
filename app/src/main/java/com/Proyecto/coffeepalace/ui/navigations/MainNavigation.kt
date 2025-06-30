package com.Proyecto.coffeepalace.ui.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.Proyecto.coffeepalace.ui.Screens.Customer.Cart.CartScreen
import com.Proyecto.coffeepalace.ui.Screens.Customer.Category.CategoryScreen
import com.Proyecto.coffeepalace.ui.Screens.Customer.Login.LoginScreen
import com.Proyecto.coffeepalace.ui.Screens.Customer.Forgot.ForgotPasswordScreen
import com.Proyecto.coffeepalace.ui.Screens.Customer.Home.HomeScreen
import com.Proyecto.coffeepalace.ui.Screens.Customer.ProductDetail.ProductDetailScreen
import com.Proyecto.coffeepalace.ui.Screens.Customer.Profile.ProfileScreen
import com.Proyecto.coffeepalace.ui.Screens.Search.RecipeDetailScreen
import com.Proyecto.coffeepalace.ui.Screens.Customer.Search.SearchScreen
import com.Proyecto.coffeepalace.ui.Screens.Customer.SignUp.SignUpScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    startDestination: String = NavigationRoutes.LOGIN
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Pantalla de Login
        composable(NavigationRoutes.LOGIN) {
            LoginScreen(
                onNavigateToForgotPassword = {
                    navController.navigate(NavigationRoutes.FORGOT_PASSWORD)
                },
                onNavigateToSignUp = {
                    navController.navigate(NavigationRoutes.SIGN_UP)
                },
                onLoginSuccess = {
                    navController.navigate(NavigationRoutes.SEARCH) {
                        popUpTo(NavigationRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // Pantalla de Forgot Password
        composable(NavigationRoutes.FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                navController = navController
            )
        }

        // Pantalla de Sign Up (placeholder)
        composable(NavigationRoutes.SIGN_UP) {
            SignUpScreen()
        }

        // Pantalla de Home
        composable(NavigationRoutes.HOME) {
            HomeScreen(navController = navController)
        }

        // Pantalla de Búsqueda
        composable(NavigationRoutes.SEARCH) {
            SearchScreen(navController = navController)
        }

        // Pantalla de Categorías
        composable(NavigationRoutes.CATEGORY) {
            CategoryScreen(navController = navController)
        }

        // Pantalla de Perfil
        composable(NavigationRoutes.PROFILE) {
            ProfileScreen(navController = navController)
        }

        // Pantalla de Carrito
        composable(NavigationRoutes.CART) {
            CartScreen(navController = navController)
        }

        // Pantalla de Detalle de Producto
        composable(
            route = NavigationRoutes.PRODUCT_DETAIL,
            arguments = listOf(
                navArgument("productId") { type = NavType.IntType },
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            val userId = backStackEntry.arguments?.getInt("userId") ?: 1

            ProductDetailScreen(
                navController = navController,
                idProducto = productId,
                idUsuario = userId
            )
        }

        // Pantalla de Detalle de Receta
        composable(
            route = NavigationRoutes.RECIPE_DETAIL,
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
            RecipeDetailScreen(
                navController = navController,
                recipeId = recipeId
            )
        }
    }
}



/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(navController: NavHostController) {

    val productDetailRoute = "product_detail/{idProducto}/{idUsuario}"

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) },
                onNavigateToForgotPassword = { navController.navigate(Screen.ForgotPassword.route) },
                onLoginSuccess = { navController.navigate(Screen.ProductDetail.route) }
            )
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController = navController)
        }

        composable(
            route = productDetailRoute,
            arguments = listOf(
                navArgument("idProducto") { type = NavType.IntType },
                navArgument("idUsuario") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val idProducto = backStackEntry.arguments?.getInt("idProducto") ?: 0
            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0

            ProductDetailScreen(
                navController = navController,
                idProducto = idProducto,
                idUsuario = idUsuario
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(navController = navController)
        }

        /*composable("recipe_detail/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
            recipeId?.let {
                RecipeDetailScreen(navController = navController, recipeId = it)
            }
        }*/
    }
}
*/

