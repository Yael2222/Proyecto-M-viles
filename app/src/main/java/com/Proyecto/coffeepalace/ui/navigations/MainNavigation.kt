package com.Proyecto.coffeepalace.ui.navigations

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.Proyecto.coffeepalace.ui.Screens.HomeFiltered.HomeFiltered
import com.Proyecto.coffeepalace.ui.Screens.HomePage.HomePage
import com.Proyecto.coffeepalace.ui.Screens.Profile.ProfileScreen
import com.Proyecto.coffeepalace.ui.components.BackAppBar
import com.Proyecto.coffeepalace.ui.components.CoffeePalaceScaffold

@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            CoffeePalaceScaffold(
                navigateToProfileScreen = {
                    navController.navigate("profile")
                },
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
                navigateToProfileScreen = {
                    navController.navigate("profile")
                },
                content = { innerPadding ->
                    HomeFiltered(
                        modifier = Modifier.padding(innerPadding)
                    )
                })
        }
        composable("profile") {
            CoffeePalaceScaffold(
                navigateToProfileScreen = {
                    navController.navigate("profile")
                },
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
    }
}
