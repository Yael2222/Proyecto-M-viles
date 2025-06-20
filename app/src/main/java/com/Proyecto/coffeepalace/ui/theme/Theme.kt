package com.Proyecto.coffeepalace.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme

private val LightColorScheme = lightColorScheme(
    primary = BrownCoffee,
    onPrimary = Color.White,
    secondary = BrownCoffee,
    onSecondary = Color.White,
    tertiary = LightBrown,
    background = Color.White,
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val DarkColorScheme = darkColorScheme(
    primary = BrownCoffee,
    onPrimary = Color.White,
    secondary = BrownCoffee,
    onSecondary = Color.White,
    tertiary = LightBrown,
    background = Color(0xFF1C1C1C),
    surface = Color(0xFF121212),
    onBackground = Color.White,
    onSurface = Color.White,
)

@Composable
fun CoffeePalaceTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}




