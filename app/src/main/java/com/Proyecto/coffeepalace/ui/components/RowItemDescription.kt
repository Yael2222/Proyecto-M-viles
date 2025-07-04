package com.Proyecto.coffeepalace.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun RowItemDescription(
    title: String,
    description: String,
    titleColor: Color = Color.Black,
    titleFontWeight: FontWeight = FontWeight.Normal,
    descriptionColor: Color = Color.Gray,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, style = textStyle, color = titleColor, fontWeight = titleFontWeight)
        Text(
            description,
            style = textStyle,
            color = descriptionColor,
            fontWeight = descriptionFontWeight
        )
    }
}