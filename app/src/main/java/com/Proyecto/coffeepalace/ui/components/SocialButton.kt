package com.Proyecto.coffeepalace.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha // Para deshabilitar visualmente
import androidx.compose.ui.layout.ContentScale

@Composable
fun SocialButton(
    assetName: String, // ej. "icon_google.png"
    onClick: () -> Unit = {}, // Click listener
    enabled: Boolean = true // Para controlar si el botón está activo
) {
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context)
            .data("file:///android_asset/$assetName")
            .build()
    )

    Surface(
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .then(
                if (enabled) Modifier.clickable { onClick() }
                else Modifier.alpha(0.5f) // Hace el botón un poco transparente si está deshabilitado
            ),
        color = Color.White, // Fondo blanco para el botón social
        shadowElevation = 4.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painter,
                contentDescription = "Social Login Button",
                modifier = Modifier.size(30.dp), // Tamaño del icono dentro del botón
                contentScale = ContentScale.Fit
            )
        }
    }
}