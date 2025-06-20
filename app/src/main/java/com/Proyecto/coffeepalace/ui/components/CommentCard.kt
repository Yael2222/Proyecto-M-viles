package com.Proyecto.coffeepalace.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CommentCard(comment: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(end = 8.dp)
            .width(160.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(Modifier.padding(12.dp)) {
            Text("🍨 Affogato", fontWeight = FontWeight.Bold)
            Text(comment.split(" - ").getOrNull(1) ?: "", fontSize = 12.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(4) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(14.dp))
                }
                Text(" 56,890", fontSize = 10.sp)
            }
        }
    }
}