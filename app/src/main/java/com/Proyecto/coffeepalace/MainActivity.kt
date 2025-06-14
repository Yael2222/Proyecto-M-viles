package com.Proyecto.coffeepalace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.rememberNavController
import com.Proyecto.coffeepalace.ui.theme.CoffeePalaceTheme
import com.Proyecto.coffeepalace.ui.navigations.MainNavigation

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoffeePalaceTheme {
                MainNavigation(navController = rememberNavController())
            }
        }
    }
}