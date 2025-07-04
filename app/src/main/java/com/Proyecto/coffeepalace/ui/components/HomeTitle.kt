package com.Proyecto.coffeepalace.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.ui.theme.Brown
import com.Proyecto.coffeepalace.ui.theme.black

@Composable
fun HomeTitle(
    title: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Bold,
    color: Color = Brown,
    fontStyle: TextStyle = MaterialTheme.typography.headlineSmall
) {
    Text(
        text = title,
        style = fontStyle,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = color,
        textAlign = TextAlign.Start,
        fontWeight = fontWeight
    )
}