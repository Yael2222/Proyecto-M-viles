package com.Proyecto.coffeepalace.ui.navigations

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Proyecto.coffeepalace.ui.Screens.CarDetails.CarDetailsScreen
import com.Proyecto.coffeepalace.ui.Screens.Checkout.CheckoutScreen
import com.Proyecto.coffeepalace.ui.Screens.ConfirmationPayment.ConfirmationPaymentScreen
import com.Proyecto.coffeepalace.ui.Screens.GetStarted.GetStartedScreen
import com.Proyecto.coffeepalace.ui.Screens.HomeFiltered.HomeFiltered
import com.Proyecto.coffeepalace.ui.Screens.HomePage.HomePage
import com.Proyecto.coffeepalace.ui.Screens.PlaceOrderDetails.PlaceOrderDetailsScreen
import com.Proyecto.coffeepalace.ui.Screens.Profile.ProfileScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.Category.CategoryScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.Category.CategoryViewModel
import com.Proyecto.coffeepalace.ui.Screens.Seller.HomeSeller.HomeSellerScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.HomeSeller.HomeSellerViewModel
import com.Proyecto.coffeepalace.ui.components.BackAppBar
import com.Proyecto.coffeepalace.ui.components.CoffeePalaceScaffold

sealed class Screens(val route: String) {
    object HomeSeller : Screens("home_seller")
    object AddProduct : Screens("add_product")
    object ViewStock : Screens("view_stock")
    object ViewUsers : Screens("view_users")
    object Estadistics : Screens("estadistics")
    object Comments : Screens("comments")
    object Category : Screens("category")
    object HomeClient : Screens("home_client")
    object ProfileClient : Screens("profile_client")
    object CarDetailsClient : Screens("car_details_client")
    object CheckoutClient : Screens("checkout_client")
    object ConfirmationPaymentClient : Screens("confirmation_payment_client")
    object HomeFilteredClient : Screens("home_filtered_client")
    object OrderDetailsClient : Screens("order_details_client")
    object GetStartedClient : Screens("get_started_client")
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    val navigateToProfileScreen = { navController.navigate(Screens.ProfileClient.route) }
    val navigateToCarDetails = { navController.navigate(Screens.CarDetailsClient.route) }

    NavHost(navController = navController, startDestination = Screens.HomeClient.route) {

        composable(route = Screens.HomeSeller.route) {
            val homeSellerViewModel: HomeSellerViewModel = viewModel()
            HomeSellerScreen(viewModel = homeSellerViewModel, navController = navController)
        }

        composable(route = Screens.Category.route) {
            val categoryViewModel: CategoryViewModel = viewModel()
            CategoryScreen(viewModel = categoryViewModel, navController = navController)
        }

        // Clientes
        composable(Screens.HomeClient.route) {
            CoffeePalaceScaffold(
                navigateToProfileScreen = navigateToProfileScreen,
                navigateToCarDetails = navigateToCarDetails,
                content = { innerPadding ->
                    HomePage(
                        modifier = Modifier.padding(innerPadding),
                        navigateToHomeFiltered = { filteredType ->
                            navController.navigate(Screens.HomeFilteredClient.route)
                        }
                    )
                })
        }
        composable(Screens.HomeFilteredClient.route) {
            CoffeePalaceScaffold(
                navigateToProfileScreen = navigateToProfileScreen,
                navigateToCarDetails = navigateToCarDetails,
                content = { innerPadding ->
                    HomeFiltered(
                        modifier = Modifier.padding(innerPadding)
                    )
                })
        }
        composable(Screens.ProfileClient.route) {
            CoffeePalaceScaffold(
                navigateToCarDetails = navigateToCarDetails,
                topBar = {
                    BackAppBar(title = "Profile", onBackClick = {
                        navController.popBackStack()
                    })
                },
                content = { innerPadding ->
                    ProfileScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                })
        }
        composable(Screens.CarDetailsClient.route) {
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
                        navigateToCheckout = { navController.navigate(Screens.CheckoutClient.route) },
                    )
                })
        }
        composable(Screens.CheckoutClient.route) {
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
                        modifier = Modifier.padding(innerPadding),
                        navigateToPayment = {navController.navigate(Screens.ConfirmationPaymentClient.route)}
                        )
                })
        }
        composable(Screens.OrderDetailsClient.route) {
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
                    BackAppBar(title = "Get Started", onBackClick = {
                        navController.popBackStack()
                    })
                },
                content = { innerPadding ->
                    GetStartedScreen(
                        modifier = Modifier.padding(innerPadding),
                    )
                })
        }

        // Agrega aquí los otros destinos si los necesitas
    }
}
