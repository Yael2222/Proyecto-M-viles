package com.Proyecto.coffeepalace.ui.navigations

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.Proyecto.coffeepalace.ui.Screens.CarDetails.CarDetailsScreen
import com.Proyecto.coffeepalace.ui.Screens.Checkout.CheckoutScreen
import com.Proyecto.coffeepalace.ui.Screens.ConfirmationPayment.ConfirmationPaymentScreen
import com.Proyecto.coffeepalace.ui.Screens.HomeFiltered.HomeFiltered
import com.Proyecto.coffeepalace.ui.Screens.HomePage.HomePage
import com.Proyecto.coffeepalace.ui.Screens.PlaceOrderDetails.PlaceOrderDetailsScreen
import com.Proyecto.coffeepalace.ui.Screens.Profile.ProfileScreen
import com.Proyecto.coffeepalace.ui.components.BackAppBar
import com.Proyecto.coffeepalace.ui.components.CoffeePalaceScaffold

@Composable
fun MainNavigation(navController: NavHostController) {
    val navigateToProfileScreen = { navController.navigate("profile") }
    val navigateToCarDetails = { navController.navigate("carDetail") }

    NavHost(
        navController = navController,
        startDestination = "confirmationPayment",
    ) {
        composable("main") {
            CoffeePalaceScaffold(
                navigateToProfileScreen = navigateToProfileScreen,
                navigateToCarDetails = navigateToCarDetails,
                content = { innerPadding ->
                    HomePage(
                        modifier = Modifier.padding(innerPadding),
                        navigateToHomeFiltered = { filteredType ->
                            navController.navigate("homeFiltered")
                        }
                    )
                })
        }
        composable("homeFiltered") {
            CoffeePalaceScaffold(
                navigateToProfileScreen = navigateToProfileScreen,
                navigateToCarDetails = navigateToCarDetails,
                content = { innerPadding ->
                    HomeFiltered(
                        modifier = Modifier.padding(innerPadding)
                    )
                })
        }
        composable("profile") {
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
        composable("carDetail") {
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
                        navigateToCheckout = { navController.navigate("checkout") },
                    )
                })
        }
        composable("checkout") {
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

                        )
                })
        }
        composable("orderDetails") {
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
        composable("confirmationPayment") {
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
    }
}
