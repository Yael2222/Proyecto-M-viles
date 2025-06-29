package com.Proyecto.coffeepalace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import com.Proyecto.coffeepalace.di.AppContainer
import com.Proyecto.coffeepalace.ui.navigations.NavGraph
import com.Proyecto.coffeepalace.ui.theme.CoffeePalaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppContainer.initialize(applicationContext)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setContent {
            CoffeePalaceTheme {
                NavGraph()
            }
        }
    }
}
