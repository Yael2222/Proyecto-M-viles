package com.Proyecto.coffeepalace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.platform.LocalContext
import com.Proyecto.coffeepalace.ui.navigations.NavGraph
import com.Proyecto.coffeepalace.ui.theme.CoffeePalaceTheme
import com.Proyecto.coffeepalace.utils.isFirstLaunch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setContent {
            CoffeePalaceTheme {
                var isFirstLaunch = isFirstLaunch(LocalContext.current)
                NavGraph()
            }

        }
    }
}
