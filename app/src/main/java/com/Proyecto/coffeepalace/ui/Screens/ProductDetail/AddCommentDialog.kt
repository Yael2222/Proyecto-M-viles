package com.Proyecto.coffeepalace.ui.Screens.ProductDetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults

@Composable
fun AddCommentBottomSheet(
viewModel: ProductViewModel,
onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            "Add a comment",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )

        Spacer(Modifier.height(16.dp))


        // Estrellas de calificación
        Row {
            (1..5).forEach { starIndex ->
                Icon(
                    imageVector = if (starIndex <= viewModel.newRating.value) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = "Rating Star",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .clickable { viewModel.newRating.value = starIndex }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.newCommentText.value,
            onValueChange = { viewModel.newCommentText.value = it },
            placeholder = { Text("add your comments") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF8A4F2E),
                unfocusedBorderColor = Color(0xFF8A4F2E)
            )
        )

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
            Spacer(Modifier.width(8.dp))
            TextButton(
                onClick = {
                    viewModel.addComment()
                    onDismiss()
                }
            ) {
                Text("Save", color = Color(0xFF8A4F2E))
            }
        }
    }
}
