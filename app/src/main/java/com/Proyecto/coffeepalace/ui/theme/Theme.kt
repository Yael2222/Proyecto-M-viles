package com.Proyecto.coffeepalace.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4A2C20),
    onPrimary = Color.White,
    secondary = Color(0xFFB55B00),
    onSecondary = Color.White,
    tertiary = Color(0xFFD7A86E),
    background = Color.White,
    onBackground = Color(0xFF2E1B10),
    surface = Color.White,
    onSurface = Color(0xFF2E1B10)
)

@Composable
fun CoffeePalaceTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}




