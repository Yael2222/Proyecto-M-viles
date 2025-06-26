package com.Proyecto.coffeepalace.ui.navigations

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider // Importación necesaria para ViewModelProvider.Factory
import androidx.lifecycle.viewmodel.compose.viewModel // Importación necesaria para la función viewModel()
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Importa todas tus pantallas y ViewModels de forma única
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
import com.Proyecto.coffeepalace.di.AppContainer
/*
import com.Proyecto.coffeepalace.ui.Screens.Login.LoginScreen
import com.Proyecto.coffeepalace.ui.Screens.Forgot.ForgotPasswordScreen
import com.Proyecto.coffeepalace.ui.Screens.ProductDetail.ProductDetailScreen
import com.Proyecto.coffeepalace.ui.Screens.Search.RecipeDetailScreen
import com.Proyecto.coffeepalace.ui.Screens.Search.SearchScreen
import com.Proyecto.coffeepalace.ui.Screens.Splash.SplashScreen
import com.Proyecto.coffeepalace.ui.Screens.SignUp.SignUpScreen
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()

    val categoryViewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CategoryViewModel(AppContainer.categoryRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class for CategoryViewModel")
        }
    }
    val userViewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ViewUsersViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ViewUsersViewModel(AppContainer.userRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class for ViewUsersViewModel")
        }
    }
    val ingredienteViewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(addIngredienteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return addIngredienteViewModel(AppContainer.ingredienteRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class for addIngredienteViewModel")
        }
    }
    val addProductViewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddProductViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddProductViewModel(AppContainer.productRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class for AddProductViewModel")
        }
    }
    val deleteProductViewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DeleteProductViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DeleteProductViewModel(AppContainer.productRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class for DeleteProductViewModel")
        }
    }

    NavHost(navController = navController, startDestination = Screens.HomeSeller.route) {

        composable(route = Screens.HomeSeller.route) {
            val homeSellerViewModel: HomeSellerViewModel = viewModel()
            HomeSellerScreen(viewModel = homeSellerViewModel, navController = navController)
        }

        composable(route = Screens.Category.route) {
            val categoryViewModel: CategoryViewModel = viewModel(factory = categoryViewModelFactory)
            CategoryScreen(viewModel = categoryViewModel, navController = navController)
        }

        composable(route = Screens.AddProduct.route) {
            val addProductViewModel: AddProductViewModel = viewModel(factory = addProductViewModelFactory)
            AddProductScreen(viewModel = addProductViewModel, navController = navController)
        }

        composable(route = Screens.Ingrediente.route) {
            val ingredienteViewModel: addIngredienteViewModel = viewModel(factory = ingredienteViewModelFactory)
            addIngredienteScreen(viewModel = ingredienteViewModel, navController = navController)
        }


        composable(route = Screens.AddReceta.route) {
            val addRecetaViewModel: AddRecetaViewModel = viewModel()
            AddRecetaScreen(viewModel = addRecetaViewModel, navController = navController)
        }

        composable(route = Screens.DeleteProduct.route) {
            val deleteProductViewModel: DeleteProductViewModel = viewModel(factory = deleteProductViewModelFactory)
            DeleteProductScreen(viewModel = deleteProductViewModel, navController = navController)
        }

        composable(route = Screens.ViewUsers.route) {
            val viewUsersViewModel: ViewUsersViewModel = viewModel(factory = userViewModelFactory)
            ViewUsersScreen(viewModel = viewUsersViewModel, navController = navController)
        }

        composable(route = Screens.DeleteReceta.route) {
            val deleteRecetaViewModel: DeleteRecetaViewModel = viewModel()
            DeleteRecetaScreen(viewModel = deleteRecetaViewModel, navController = navController)
        }

        /*

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

  */
    }
}