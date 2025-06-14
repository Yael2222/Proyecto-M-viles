package com.Proyecto.coffeepalace.ui.navigations

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.Proyecto.coffeepalace.ui.Screens.Login.LoginScreen
import com.Proyecto.coffeepalace.ui.Screens.Forgot.ForgotPasswordScreen
import com.Proyecto.coffeepalace.ui.Screens.ProductDetail.ProductDetailScreen
import com.Proyecto.coffeepalace.ui.Screens.Search.RecipeDetailScreen
import com.Proyecto.coffeepalace.ui.Screens.Search.SearchScreen
import com.Proyecto.coffeepalace.ui.Screens.Splash.SplashScreen
import com.Proyecto.coffeepalace.ui.Screens.SignUp.SignUpScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(navController: NavHostController) {

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
                onBackToLogin = { navController.popBackStack(Screen.Login.route, false) }
            )
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.ProductDetail.route) {
            ProductDetailScreen(navController = navController)
        }

        composable(Screen.Search.route) {
            SearchScreen(navController = navController)
        }

        composable("recipe_detail/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
            recipeId?.let {
                RecipeDetailScreen(navController = navController, recipeId = it)
            }
        }
    }
}
