package com.Proyecto.coffeepalace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold

import androidx.compose.ui.Modifier
import com.Proyecto.coffeepalace.ui.theme.CoffeePalaceTheme

import androidx.navigation.compose.rememberNavController
import com.Proyecto.coffeepalace.ui.navigations.MainNavigation



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoffeePalaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //HomePage(Modifier.padding(innerPadding))
                    val navController = rememberNavController()
                    // 2. Llama a tu MainNavigation y le pasas el NavController
                    MainNavigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

