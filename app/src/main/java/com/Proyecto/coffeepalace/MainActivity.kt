package com.Proyecto.coffeepalace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.rememberNavController
import com.Proyecto.coffeepalace.ui.theme.CoffeePalaceTheme
import com.Proyecto.coffeepalace.ui.navigations.MainNavigation
import com.Proyecto.coffeepalace.ui.Screens.Seller.ViewStock.ViewStockViewModel

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private val viewStockViewModel: ViewStockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeePalaceTheme {
                val navController = rememberNavController()

                // Navegación principal (puedes pasar el viewModel si lo usas dentro)
                MainNavigation(
                    navController = navController,
                    viewStockViewModel = viewStockViewModel
                )
            }
        }
    }
}