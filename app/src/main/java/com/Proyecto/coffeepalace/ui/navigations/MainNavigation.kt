package com.Proyecto.coffeepalace.ui.navigations

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Proyecto.coffeepalace.Data.LoginViewModelFactory
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
import com.Proyecto.coffeepalace.ui.Screens.Seller.Ordenes.OrderScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.Ordenes.OrderViewModel
import com.Proyecto.coffeepalace.ui.Screens.Splash.SplashScreen
import com.Proyecto.coffeepalace.ui.Screens.Login.LoginScreen
import com.Proyecto.coffeepalace.ui.Screens.SignUp.SignUpScreen
import com.Proyecto.coffeepalace.ui.Screens.ForgotPassword.ForgotPasswordScreen
import com.Proyecto.coffeepalace.ui.Screens.SignUp.SignUpViewModelFactory
import com.Proyecto.coffeepalace.ui.Screens.ForgotPassword.ForgotPasswordViewModelFactory
import com.Proyecto.coffeepalace.ui.Screens.Seller.HomeSellerViewModelFactory
import com.Proyecto.coffeepalace.di.AppContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()

    val loginViewModelFactory = LoginViewModelFactory(navController.context) // Pasa el contexto
    val signUpViewModelFactory = SignUpViewModelFactory()
    val forgotPasswordViewModelFactory = ForgotPasswordViewModelFactory()
    val homeSellerViewModelFactory = HomeSellerViewModelFactory() // <--- Instancia la factoría


    val orderViewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OrderViewModel(AppContainer.orderRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class for OrderViewModel")
        }
    }

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
    val addRecetaViewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddRecetaViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                // AddRecetaViewModel requiere dos repositorios
                return AddRecetaViewModel(
                    AppContainer.recetaRepository,
                    AppContainer.productRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class for AddRecetaViewModel")
        }
    }

    val deleteRecetaViewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DeleteRecetaViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DeleteRecetaViewModel(AppContainer.recetaRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class for DeleteRecetaViewModel")
        }
    }
    NavHost(navController = navController, startDestination = Screens.Splash.route) {

        composable(route = Screens.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screens.HomeSeller.route) {
            val homeSellerViewModel: HomeSellerViewModel = viewModel(factory = homeSellerViewModelFactory) // <--- ¡CAMBIO AQUÍ! Usa la factoría
            HomeSellerScreen(viewModel = homeSellerViewModel, navController = navController)
        }
        composable(route = Screens.Login.route) { // <--- RUTA DE LOGIN
            LoginScreen(
                onNavigateToForgotPassword = { navController.navigate(Screens.ForgotPassword.route) },
                onNavigateToSignUp = { navController.navigate(Screens.SignUp.route) },
                onLoginSuccess = { navController.navigate(Screens.HomeSeller.route) {
                    popUpTo(Screens.Login.route) { inclusive = true } // Eliminar Login del back stack
                }}
            )
        }
        composable(route = Screens.SignUp.route) { // <--- RUTA DE SIGNUP
            SignUpScreen(
                onNavigateToLogin = { navController.navigate(Screens.Login.route) {
                    popUpTo(Screens.SignUp.route) { inclusive = true } // Eliminar SignUp del back stack
                } },
                onBackToLogin = { navController.popBackStack() }
            )
        }
        composable(route = Screens.ForgotPassword.route) {
            ForgotPasswordScreen(
                navController = navController,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(route = Screens.Orders.route) {
            val orderViewModel: OrderViewModel = viewModel(factory = orderViewModelFactory)
            OrderScreen(viewModel = orderViewModel, navController = navController)
        }



        composable(route = Screens.Category.route) {
            val categoryViewModel: CategoryViewModel = viewModel(factory = categoryViewModelFactory)
            CategoryScreen(viewModel = categoryViewModel, navController = navController)
        }

        composable(route = Screens.AddProduct.route) {
            val addProductViewModel: AddProductViewModel =
                viewModel(factory = addProductViewModelFactory)
            AddProductScreen(viewModel = addProductViewModel, navController = navController)
        }

        composable(route = Screens.Ingrediente.route) {
            val ingredienteViewModel: addIngredienteViewModel =
                viewModel(factory = ingredienteViewModelFactory)
            addIngredienteScreen(viewModel = ingredienteViewModel, navController = navController)
        }


        composable(route = Screens.AddReceta.route) {
            val addRecetaViewModel: AddRecetaViewModel =
                viewModel(factory = addRecetaViewModelFactory)
            AddRecetaScreen(viewModel = addRecetaViewModel, navController = navController)
        }

        composable(route = Screens.DeleteProduct.route) {
            val deleteProductViewModel: DeleteProductViewModel =
                viewModel(factory = deleteProductViewModelFactory)
            DeleteProductScreen(viewModel = deleteProductViewModel, navController = navController)
        }

        composable(route = Screens.ViewUsers.route) {
            val viewUsersViewModel: ViewUsersViewModel = viewModel(factory = userViewModelFactory)
            ViewUsersScreen(viewModel = viewUsersViewModel, navController = navController)
        }

        composable(route = Screens.DeleteReceta.route) {
            val deleteRecetaViewModel: DeleteRecetaViewModel =
                viewModel(factory = deleteRecetaViewModelFactory)
            DeleteRecetaScreen(viewModel = deleteRecetaViewModel, navController = navController)
        }

    }
}