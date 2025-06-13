package com.Proyecto.coffeepalace.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Proyecto.coffeepalace.R

@Composable
fun CoffeeBannerWithCircle(
    coffeeCupResId: Int // ID de recurso de la imagen de la taza de café
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Contenedor circular con la taza de café
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color(0xFF6F4E37)) // Color café del círculo
        ) {
            Image(
                painter = painterResource(id = coffeeCupResId),
                contentDescription = "Coffee cup",
                modifier = Modifier.size(60.dp),
                colorFilter = ColorFilter.tint(Color.White) // Asegura que la taza sea blanca
            )
        }

        // Textos
        Text(
            text = "Your coffee, your style,",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                letterSpacing = 0.5.sp
            ),
            color = Color(0xFF4E342E), // Color café oscuro
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 24.dp)
        )

        Text(
            text = "let's go for that energy!",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            ),
            color = Color(0xFF8D6E63), // Color café más claro
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CoffeeBannerPreview() {
    MaterialTheme {
        // Necesitarás reemplazar R.drawable.coffee_cup con tu recurso real
        CoffeeBannerWithCircle(coffeeCupResId = R.drawable.ic_launcher_foreground)
    }
}