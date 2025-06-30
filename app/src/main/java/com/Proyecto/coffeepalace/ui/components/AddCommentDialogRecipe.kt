package com.Proyecto.coffeepalace.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.ui.Screens.Customer.RecipeDetail.RecipeDetailViewModel

@Composable
fun AddCommentBottomSheetRecipe(
    viewModel: RecipeDetailViewModel,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {
    val rating by viewModel.newCommentRating.collectAsState()
    val commentText by viewModel.newCommentText.collectAsState()

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

        // Estrellas
        Row {
            (1..5).forEach { index ->
                Icon(
                    imageVector = if (index <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = "Rating $index",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .clickable { viewModel.updateCommentRating(index) }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = commentText,
            onValueChange = { viewModel.updateCommentText(it) },
            placeholder = { Text("Write your comment...") },
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
                    if (commentText.isNotBlank()) {
                        onSubmit()
                    }
                }
            ) {
                Text("Submit", color = Color(0xFF8A4F2E))
            }
        }
    }
}
