package com.Proyecto.coffeepalace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.rememberNavController
import com.Proyecto.coffeepalace.ui.navigations.MainNavigation
import com.Proyecto.coffeepalace.ui.theme.CoffeePalaceTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setContent {
            CoffeePalaceTheme {
                val navController = rememberNavController()

                MainNavigation(
                    navController = navController
                )
            }
        }
    }
}
