package com.Proyecto.coffeepalace.ui.navigations

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.Proyecto.coffeepalace.ui.Screens.HomeFiltered.HomeFiltered
import com.Proyecto.coffeepalace.ui.Screens.HomePage.HomePage
import com.Proyecto.coffeepalace.ui.components.CoffeePalaceScaffold

@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            CoffeePalaceScaffold(content = { innerPadding ->
                HomePage(
                    modifier = Modifier.padding(innerPadding),
                    navigateToHomeFiltered = { filteredType ->
                        navController.navigate("homeFiltered")
                    }
                )
            })
        }
        composable("homeFiltered") {
            CoffeePalaceScaffold(content = { innerPadding ->
                HomeFiltered(
                    modifier = Modifier.padding(innerPadding)
                )
            })
        }
    }
}
