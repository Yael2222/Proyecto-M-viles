package com.Proyecto.coffeepalace.utils

import android.content.Context

fun isFirstLaunch(context: Context): Boolean {
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val isFirstTime = prefs.getBoolean("is_first_launch", true)
    if (isFirstTime) {
        prefs.edit().putBoolean("is_first_launch", false).apply()
    }
    return isFirstTime
}
