package com.Proyecto.coffeepalace.ui.navigations

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.Proyecto.coffeepalace.ui.Screens.Client.CarDetails.CarDetailsScreen
import com.Proyecto.coffeepalace.ui.Screens.Client.CarDetails.CarDetailsViewModel
import com.Proyecto.coffeepalace.ui.Screens.Client.Checkout.CheckoutScreen
import com.Proyecto.coffeepalace.ui.Screens.Client.Checkout.CheckoutViewModel
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
import com.Proyecto.coffeepalace.ui.Screens.Seller.Category.CategoryScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.Category.CategoryViewModel
import com.Proyecto.coffeepalace.ui.Screens.Seller.HomeSeller.HomeSellerScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.HomeSeller.HomeSellerViewModel
import com.Proyecto.coffeepalace.ui.components.BackAppBar
import com.Proyecto.coffeepalace.ui.components.CoffeePalaceScaffold
import com.Proyecto.coffeepalace.ui.components.NextScreenAppBar

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
    object EntryPoints : Screens("entry_points")
}

@Composable
fun NavGraph(
    isFirstLaunch: Boolean = true
) {
    val navController = rememberNavController()

    val navigateToProfileScreen = { navController.navigate(Screens.ProfileClient.route) }
    val navigateToCarDetails = { navController.navigate(Screens.CarDetailsClient.route) }
    fun navigateToPlaceOrderDetails(orderId: Long) {
        navController.navigate("${Screens.OrderDetailsClient.route}/$orderId")
    }

    NavHost(navController = navController, startDestination = Screens.EntryPoints.route) {

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
                isFirstLaunch = isFirstLaunch,
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