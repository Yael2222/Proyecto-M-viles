package com.Proyecto.coffeepalace

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import com.Proyecto.coffeepalace.di.AppContainer
import com.Proyecto.coffeepalace.ui.navigations.NavGraph
import com.Proyecto.coffeepalace.ui.theme.CoffeePalaceTheme
import kotlinx.coroutines.flow.MutableSharedFlow

object PayPalDeepLinkBus {
    val deepLink = MutableSharedFlow<Intent>(extraBufferCapacity = 1)
}

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

    override fun onNewIntent(intent: Intent) {
        println("MainActivity onNewIntent()1")
        super.onNewIntent(intent)
        emitIfPayPal(intent)
    }

    private fun emitIfPayPal(intent: Intent?) {
        intent?.data?.host
            ?.takeIf { it == "paypalpay" }
            ?.let { PayPalDeepLinkBus.deepLink.tryEmit(intent) }
    }

}