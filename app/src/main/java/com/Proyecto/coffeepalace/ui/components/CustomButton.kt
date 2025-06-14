package com.Proyecto.coffeepalace.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Proyecto.coffeepalace.ui.theme.Brown

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Brown,
    textColor: Color = Color.White,
    enabled: Boolean = true,
    minWidth: Dp = 120.dp,
    height: Dp = 48.dp,
    padding: PaddingValues = PaddingValues(horizontal = 24.dp),
    shape: RoundedCornerShape = RoundedCornerShape(4.dp),
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    ),
    disabledBackgroundColor: Color = backgroundColor.copy(alpha = 0.5f),
    disabledTextColor: Color = textColor.copy(alpha = 0.5f)
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .widthIn(min = minWidth)
            .height(height),
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
            disabledContainerColor = disabledBackgroundColor,
            disabledContentColor = disabledTextColor
        ),
        contentPadding = padding
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}