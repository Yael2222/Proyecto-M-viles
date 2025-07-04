package com.Proyecto.coffeepalace
import android.app.Application
import com.Proyecto.coffeepalace.di.AppContainer

class CoffeePalaceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppContainer.initialize(this)
    }
}