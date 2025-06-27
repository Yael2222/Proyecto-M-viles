package com.Proyecto.coffeepalace.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CommentCard(
    text: String,
    stars: Int
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(250.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                repeat(5) { index ->
                    val starColor = if (index < stars) Color(0xFFFFC107) else Color(0xFFD3D3D3)
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = starColor
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text)
        }
    }
}
