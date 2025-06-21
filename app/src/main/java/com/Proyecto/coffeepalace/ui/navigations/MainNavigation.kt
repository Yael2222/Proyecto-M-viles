package com.Proyecto.coffeepalace.ui.navigations

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.Proyecto.coffeepalace.ui.Screens.Forgot.ForgotPasswordScreen
import com.Proyecto.coffeepalace.ui.Screens.Login.LoginScreen
import com.Proyecto.coffeepalace.ui.Screens.ProductDetail.ProductDetailScreen
import com.Proyecto.coffeepalace.ui.Screens.Search.RecipeDetailScreen
import com.Proyecto.coffeepalace.ui.Screens.Search.SearchScreen
import com.Proyecto.coffeepalace.ui.Screens.SignUp.SignUpScreen
import com.Proyecto.coffeepalace.ui.Screens.Splash.SplashScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.AddProduct.AddProductScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.AddProduct.AddProductViewModel
import com.Proyecto.coffeepalace.ui.Screens.Seller.Category.CategoryScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.Category.CategoryViewModel
import com.Proyecto.coffeepalace.ui.Screens.Seller.HomeSeller.HomeSellerScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.HomeSeller.HomeSellerViewModel
import com.Proyecto.coffeepalace.ui.Screens.Seller.Ingrediente.addIngredienteScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.Ingrediente.addIngredienteViewModel
import com.Proyecto.coffeepalace.ui.Screens.Seller.Product.DeleteProductScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.Product.DeleteProductViewModel
import com.Proyecto.coffeepalace.ui.Screens.Seller.Receta.AddRecetaScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.Receta.AddRecetaViewModel
import com.Proyecto.coffeepalace.ui.Screens.Seller.Receta.DeleteRecetaScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.Receta.DeleteRecetaViewModel
import com.Proyecto.coffeepalace.ui.Screens.Seller.ViewUsers.ViewUsersScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.ViewUsers.ViewUsersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(
    navController: NavHostController,
) {

    NavHost(navController = navController, startDestination = Screen.Login.route) {

        // 🔐 Cliente
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) },
                onNavigateToForgotPassword = { navController.navigate(Screen.ForgotPassword.route) },
                onLoginSuccess = {
                    // Aquí decides hacia qué Home enviar según el tipo de usuario
                    navController.navigate(Screens.HomeSeller.route)
                    // o navController.navigate(Screen.ProductDetail.route)
                }
            )
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(
                onBackToLogin = { navController.popBackStack(Screen.Login.route, false) }
            )
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController = navController)
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

        // 🛒 Vendedor
        composable(Screens.HomeSeller.route) {
            val homeSellerViewModel: HomeSellerViewModel = viewModel()
            HomeSellerScreen(viewModel = homeSellerViewModel, navController = navController)
        }

        composable(Screens.Category.route) {
            val categoryViewModel: CategoryViewModel = viewModel()
            CategoryScreen(viewModel = categoryViewModel, navController = navController)
        }

        composable(Screens.AddProduct.route) {
            val addProductViewModel: AddProductViewModel = viewModel()
            AddProductScreen(viewModel = addProductViewModel, navController = navController)
        }

        composable(Screens.Ingrediente.route) {
            val ingredienteViewModel: addIngredienteViewModel = viewModel()
            addIngredienteScreen(viewModel = ingredienteViewModel, navController = navController)
        }

        composable(Screens.AddReceta.route) {
            val addRecetaViewModel: AddRecetaViewModel = viewModel()
            AddRecetaScreen(viewModel = addRecetaViewModel, navController = navController)
        }

        composable(Screens.DeleteProduct.route) {
            val deleteProductViewModel: DeleteProductViewModel = viewModel()
            DeleteProductScreen(viewModel = deleteProductViewModel, navController = navController)
        }

        composable(Screens.ViewUsers.route) {
            val viewUsersViewModel: ViewUsersViewModel = viewModel()
            ViewUsersScreen(viewModel = viewUsersViewModel, navController = navController)
        }

        composable(Screens.DeleteReceta.route) {
            val deleteRecetaViewModel: DeleteRecetaViewModel = viewModel()
            DeleteRecetaScreen(viewModel = deleteRecetaViewModel, navController = navController)
        }

    }
}
