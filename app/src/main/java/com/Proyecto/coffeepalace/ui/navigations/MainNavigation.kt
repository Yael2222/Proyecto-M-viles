package com.Proyecto.coffeepalace.ui.navigations

import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.Proyecto.coffeepalace.Data.LoginViewModelFactory
import com.Proyecto.coffeepalace.Data.Repository.RecetaRepository
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
import com.Proyecto.coffeepalace.di.AppContainer.recetaRepository
import com.Proyecto.coffeepalace.ui.Screens.Client.HomePage.HomeScreen
import com.Proyecto.coffeepalace.ui.Screens.Client.HomePage.HomeViewModel
import com.Proyecto.coffeepalace.ui.Screens.User.UserProfileScreen
import com.Proyecto.coffeepalace.ui.Screens.Client.ProductDetail.ProductDetailScreen
import com.Proyecto.coffeepalace.ui.Screens.Client.RecetaDetail.RecetaDetailScreen
import com.Proyecto.coffeepalace.ui.Screens.Client.RecetaDetail.RecetaDetailViewModel
import com.Proyecto.coffeepalace.ui.Screens.Client.ShoppingCart.ShoppingCartScreen
import com.Proyecto.coffeepalace.ui.Screens.Search.SearchScreen

/*
import com.Proyecto.coffeepalace.ui.Screens.CategoryClient.CategoryClientScreen
import com.Proyecto.coffeepalace.ui.Screens.ShoppingCart.ShoppingCartScreen
import com.Proyecto.coffeepalace.ui.Screens.Search.SearchScreen
*/
import com.Proyecto.coffeepalace.ui.Screens.User.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    // CASTEAMOS EL CONTEXT A ComponentActivity
    val activity = context as ComponentActivity // <-- ¡ESTE ES EL CAMBIO CLAVE!

    // Asegúrate de que AppContainer ya esté inicializado antes de llegar aquí.
    // Esto se haría en MainActivity.onCreate()
    val recetaRepository = recetaRepository
    val loginViewModelFactory = LoginViewModelFactory(navController.context)
    val signUpViewModelFactory = SignUpViewModelFactory()
    val forgotPasswordViewModelFactory = ForgotPasswordViewModelFactory()
    val homeSellerViewModelFactory = HomeSellerViewModelFactory()


    val homeViewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(AppContainer.productRepository, AppContainer.categoryRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class for HomeViewModel")
        }
    }
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

    val userViewModelFactorySeller = object : ViewModelProvider.Factory { // Renombrado para evitar conflicto
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

    val userProfileViewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                // Pasa tanto authRepository como userRepository
                return UserViewModel(
                    AppContainer.authRepository, AppContainer.userRepository,
                    AppContainer.orderRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class for UserViewModel")
        }
    }
    class RecetaDetailViewModelFactory(
        private val recetaRepository: RecetaRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecetaDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RecetaDetailViewModel(recetaRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


    NavHost(navController = navController, startDestination = Screens.Splash.route) {

        composable(route = Screens.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screens.Home.route) {
            val homeViewModel: HomeViewModel = viewModel(factory = homeViewModelFactory)
            HomeScreen(navController = navController, viewModel = homeViewModel)
        }

        composable(Screens.Search.route) {
            SearchScreen(navController = navController)
        }
        composable(Screens.ShoppingCart.route) { ShoppingCartScreen(navController) } // NUEVO

        composable(
            route = "product_detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType }) // Esperamos un Int
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            if (productId != null) {
                ProductDetailScreen(navController = navController, productId = productId)
            } else {
                Text("Error: ID de producto no proporcionado.")
            }
        }
        composable(
            route = "receta_detail/{recetaId}",
            arguments = listOf(navArgument("recetaId") { type = NavType.LongType })
        ) { backStackEntry ->
            val recetaId = backStackEntry.arguments?.getLong("recetaId") ?: -1L

            // CREACIÓN MANUAL DEL VIEWMODEL USANDO UN FACTORY
            val viewModelFactory = remember {
                RecetaDetailViewModelFactory(recetaRepository)
            }
            val recetaDetailViewModel: RecetaDetailViewModel = viewModel(
                viewModelStoreOwner = activity, // Ahora 'activity' es de tipo ComponentActivity
                factory = viewModelFactory
            )

            // Pasar el ViewModel al Composable
            RecetaDetailScreen(
                navController = navController,
                recetaId = recetaId,
                viewModel = recetaDetailViewModel
            )
        }

        composable(route = Screens.User_Settings.route) {
            val userViewModel: UserViewModel = viewModel(factory = userProfileViewModelFactory) // <--- Usar la nueva factory
            UserProfileScreen(navController = navController, userViewModel = userViewModel) // <--- Usar la pantalla de perfil
        }
        composable(route = Screens.HomeSeller.route) {
            val homeSellerViewModel: HomeSellerViewModel = viewModel(factory = homeSellerViewModelFactory)
            HomeSellerScreen(viewModel = homeSellerViewModel, navController = navController)
        }
        composable(route = Screens.Login.route) {
            LoginScreen(
                navController = navController, // Pasa el navController al LoginScreen
                onNavigateToForgotPassword = { navController.navigate(Screens.ForgotPassword.route) },
                onNavigateToSignUp = { navController.navigate(Screens.SignUp.route) }
                // onLoginSuccess ya no se pasa aquí, se maneja dentro de LoginScreen con navController
            )
        }
        composable(route = Screens.SignUp.route) {
            SignUpScreen(
                navController = navController, // <-- YOU NEED TO PASS THE NAVCONTROLLER HERE
                onNavigateToLogin = { navController.navigate(Screens.Login.route) {
                    popUpTo(Screens.SignUp.route) { inclusive = true }
                } },
                onBackToLogin = { navController.popBackStack() } // Consider if this is still needed or if the TopAppBar handles back
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
            val viewUsersViewModel: ViewUsersViewModel = viewModel(factory = userViewModelFactorySeller) // Usar la factory renombrada
            ViewUsersScreen(viewModel = viewUsersViewModel, navController = navController)
        }

        composable(route = Screens.DeleteReceta.route) {
            val deleteRecetaViewModel: DeleteRecetaViewModel =
                viewModel(factory = deleteRecetaViewModelFactory)
            DeleteRecetaScreen(viewModel = deleteRecetaViewModel, navController = navController)
        }
    }
}