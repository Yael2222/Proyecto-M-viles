package com.Proyecto.coffeepalace.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = BrownCoffee,
    onPrimary = Color.White,
    secondary = BrownCoffee,
    onSecondary = Color.White,
    background = Color.White,
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    tertiary = LightBrown
)

private val DarkColorScheme = darkColorScheme(
    primary = BrownCoffee,
    onPrimary = Color.White,
    secondary = BrownCoffee,
    onSecondary = Color.White,
    background = Color(0xFF1C1C1C),
    surface = Color(0xFF121212),
    onBackground = Color.White,
    onSurface = Color.White,
    tertiary = LightBrown
)

@Composable
fun CoffeePalaceTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(), // Puedes forzar a false si quieres desactivar el modo oscuro
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
