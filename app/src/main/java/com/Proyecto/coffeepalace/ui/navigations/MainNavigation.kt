package com.Proyecto.coffeepalace.ui.navigations

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.Proyecto.coffeepalace.Data.LoginViewModelFactory
import com.Proyecto.coffeepalace.Data.Repository.CheckoutRepository
import com.Proyecto.coffeepalace.PayPalDeepLinkBus
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
import com.Proyecto.coffeepalace.ui.Screens.Client.CarDetails.CarDetailsScreen
import com.Proyecto.coffeepalace.ui.Screens.Client.CarDetails.CarDetailsViewModel
import com.Proyecto.coffeepalace.ui.Screens.Client.Checkout.CheckoutScreen
import com.Proyecto.coffeepalace.ui.Screens.Client.Checkout.CheckoutViewModel
import com.Proyecto.coffeepalace.ui.Screens.Client.Checkout.PayPalHolder
import com.Proyecto.coffeepalace.ui.Screens.Client.Checkout.PayPalManager
import com.Proyecto.coffeepalace.ui.Screens.Client.ConfirmationPayment.ConfirmationPaymentScreen
import com.Proyecto.coffeepalace.ui.Screens.Client.EntryPoints.EntryPointsScreen
import com.Proyecto.coffeepalace.ui.Screens.Client.GetStarted.GetStartedScreen
import com.Proyecto.coffeepalace.ui.Screens.Client.HomeFiltered.HomeFiltered
import com.Proyecto.coffeepalace.ui.Screens.Client.HomeFiltered.HomeFilteredViewModel
import com.Proyecto.coffeepalace.ui.Screens.Client.HomePage.HomePage
import com.Proyecto.coffeepalace.ui.Screens.Client.HomePage.HomePageViewModel
import com.Proyecto.coffeepalace.ui.Screens.Client.PlaceOrderDetails.PlaceOrderDetailsScreen
import com.Proyecto.coffeepalace.ui.Screens.Client.PlaceOrderDetails.PlaceOrderDetailsViewModel
import com.Proyecto.coffeepalace.ui.Screens.Client.Profile.ProfileScreen
import com.Proyecto.coffeepalace.ui.Screens.Client.Profile.ProfileViewModel
import com.Proyecto.coffeepalace.ui.components.BackAppBar
import com.Proyecto.coffeepalace.ui.components.CoffeePalaceScaffold
import com.Proyecto.coffeepalace.ui.components.NextScreenAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()

    val loginViewModelFactory = LoginViewModelFactory(navController.context) // Pasa el contexto
    val signUpViewModelFactory = SignUpViewModelFactory()
    val forgotPasswordViewModelFactory = ForgotPasswordViewModelFactory()
    val homeSellerViewModelFactory = HomeSellerViewModelFactory() // <--- Instancia la factoría
    val navigateToProfileScreen = { navController.navigate(Screens.ProfileClient.route) }
    val navigateToCarDetails = { navController.navigate(Screens.CarDetailsClient.route) }
    fun navigateToPlaceOrderDetails(orderId: Long) {
        navController.navigate("${Screens.OrderDetailsClient.route}/$orderId")
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

    val context = LocalActivity.current as ComponentActivity
    val backend = remember { AppContainer.checkoutRepository }
    val ppManager = remember(11) { //userid to be change when user ready
        PayPalManager(
            activity = context,
            clientId = "ATaGCafsIC-VUK4rCOuR7lK80ChEirVkzVwWVWaXrSBLhW7W7NyVcfjzw13_7ITmDJR-1EJtippTWQow",
            isSandbox = true,
            returnScheme = "coffeepalace",
            hostSchema = "paypalpay",
            createOrder = { backend.createOrder(11) },
            captureOrder = { id ->
                try {
                    println("PayPal Llamando a inicio captureOrder con id=$id")
                    backend.captureOrder(11, id)
                    println("PayPal captureOrder completado")
                } catch (e: Exception) {
                    println("PayPalCapture Error al capturar $e")
                }
            },
        ) { result ->
            when (result) {
                is PayPalManager.Result.Success -> println("Pago OK ${result.orderId}")
                PayPalManager.Result.Canceled -> print("Pago cancelado")
                is PayPalManager.Result.Failure -> println("Fallo: ${result.cause.message}")
            }
        }
    }
    LaunchedEffect(Unit) {
        PayPalDeepLinkBus.deepLink.collect { intent ->
            ppManager.handleDeepLink(intent)
        }
    }
    PayPalHolder.manager = ppManager


    NavHost(navController = navController, startDestination = Screens.HomeClient.route) {

        composable(route = Screens.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screens.HomeSeller.route) {
            val homeSellerViewModel: HomeSellerViewModel =
                viewModel(factory = homeSellerViewModelFactory) // <--- ¡CAMBIO AQUÍ! Usa la factoría
            HomeSellerScreen(viewModel = homeSellerViewModel, navController = navController)
        }
        composable(route = Screens.Login.route) { // <--- RUTA DE LOGIN
            LoginScreen(
                onNavigateToForgotPassword = { navController.navigate(Screens.ForgotPassword.route) },
                onNavigateToSignUp = { navController.navigate(Screens.SignUp.route) },
                onLoginSuccess = {
                    navController.navigate(Screens.HomeSeller.route) {
                        popUpTo(Screens.Login.route) {
                            inclusive = true
                        } // Eliminar Login del back stack
                    }
                }
            )
        }
        composable(route = Screens.SignUp.route) { // <--- RUTA DE SIGNUP
            SignUpScreen(
                onNavigateToLogin = {
                    navController.navigate(Screens.Login.route) {
                        popUpTo(Screens.SignUp.route) {
                            inclusive = true
                        } // Eliminar SignUp del back stack
                    }
                },
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

        // Clientes
        composable(Screens.HomeClient.route) {
            val viewmoModel: HomePageViewModel = viewModel()
            CoffeePalaceScaffold(
                navigateToProfileScreen = navigateToProfileScreen,
                navigateToCarDetails = navigateToCarDetails,
                content = { innerPadding ->
                    HomePage(
                        modifier = Modifier.padding(innerPadding),
                        homeViewModel = viewmoModel,
                        navigateToHomeFiltered = { categoryId ->
                            navController.navigate("${Screens.HomeFilteredClient.route}/$categoryId")
                        }
                    )
                })
        }
        composable(
            route = "${Screens.HomeFilteredClient.route}/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.LongType })
        ) { entry ->
            val categoryId = entry.arguments?.getLong("categoryId")
            val viewModel: HomeFilteredViewModel = viewModel()
            CoffeePalaceScaffold(
                navigateToProfileScreen = navigateToProfileScreen,
                navigateToCarDetails = navigateToCarDetails,
                content = { innerPadding ->
                    HomeFiltered(
                        modifier = Modifier.padding(innerPadding),
                        categoryId = categoryId,
                        viewModel = viewModel
                    )
                })
        }
        composable(Screens.ProfileClient.route) {
            val viewModel: ProfileViewModel = viewModel()
            CoffeePalaceScaffold(
                navigateToCarDetails = navigateToCarDetails,
                topBar = {
                    BackAppBar(title = "Profile", onBackClick = {
                        navController.popBackStack()
                    })
                },
                content = { innerPadding ->
                    ProfileScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                })
        }
        composable(Screens.CarDetailsClient.route) {
            val viewModel: CarDetailsViewModel = viewModel()
            CoffeePalaceScaffold(
                navigateToProfileScreen = navigateToProfileScreen,
                topBar = {
                    BackAppBar(title = "Shopping Bag", onBackClick = {
                        navController.popBackStack()
                    })
                },
                content = { innerPadding ->
                    CarDetailsScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        navigateToCheckout = { navController.navigate(Screens.CheckoutClient.route) },
                        navigateToOrderDetails = { orderId ->
                            navigateToPlaceOrderDetails(orderId)
                        }
                    )
                })
        }
        composable(Screens.CheckoutClient.route) {
            val checkoutViewModel = viewModel<CheckoutViewModel>()
            CoffeePalaceScaffold(
                navigateToProfileScreen = navigateToProfileScreen,
                navigateToCarDetails = navigateToCarDetails,
                topBar = {
                    BackAppBar(title = "Checkout", onBackClick = {
                        navController.popBackStack()
                    })
                },
                content = { innerPadding ->
                    CheckoutScreen(
                        ppManager = ppManager,
                        modifier = Modifier.padding(innerPadding),
                        viewModel = checkoutViewModel,
                        navigateToPayment = { navController.navigate(Screens.ConfirmationPaymentClient.route) },
                    )
                })
        }
        composable(
            route = "${Screens.OrderDetailsClient.route}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.LongType })
        ) { entry ->
            val productId = entry.arguments?.getLong("productId")
            val viewModel: PlaceOrderDetailsViewModel = viewModel()
            CoffeePalaceScaffold(
                navigateToProfileScreen = navigateToProfileScreen,
                navigateToCarDetails = navigateToCarDetails,
                topBar = {
                    BackAppBar(title = "Details", onBackClick = {
                        navController.popBackStack()
                    })
                },
                content = { innerPadding ->
                    PlaceOrderDetailsScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        productId = productId
                    )
                })
        }
        composable(Screens.ConfirmationPaymentClient.route) {
            CoffeePalaceScaffold(
                navigateToProfileScreen = navigateToProfileScreen,
                navigateToCarDetails = navigateToCarDetails,
                topBar = {
                    BackAppBar(title = "Payment", onBackClick = {
                        navController.popBackStack()
                    })
                },
                content = { innerPadding ->
                    ConfirmationPaymentScreen(
                        modifier = Modifier.padding(innerPadding),

                        )
                })
        }
        composable(Screens.GetStartedClient.route) {
            CoffeePalaceScaffold(
                navigateToProfileScreen = navigateToProfileScreen,
                navigateToCarDetails = navigateToCarDetails,
                topBar = {
                    NextScreenAppBar(
                        onNextClick = {
                            navController.navigate(Screens.HomeClient.route)
                        }
                    )
                },
                content = { innerPadding ->
                    GetStartedScreen(
                        modifier = Modifier.padding(innerPadding),
                    )
                })
        }

        composable(Screens.EntryPoints.route) {
            EntryPointsScreen(
                isFirstLaunch = false,
                navigateToGetStarted = {
                    navController.navigate(Screens.GetStartedClient.route)
                },
                navigateToHomePage = {
                    navController.navigate(Screens.HomeClient.route)
                }
            )
        }

    }

}
