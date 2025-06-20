package com.Proyecto.coffeepalace.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter

@Composable
fun SocialButton(assetName: String, onClick: () -> Unit = {}) {
    Image(
        painter = rememberAsyncImagePainter(model = "file:///android_asset/$assetName"),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(48.dp)
            .clickable { onClick() }
    )
}