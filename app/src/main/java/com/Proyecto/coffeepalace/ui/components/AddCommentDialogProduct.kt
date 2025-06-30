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
import com.Proyecto.coffeepalace.Data.Model.ComentarioProducto
import com.Proyecto.coffeepalace.ui.Screens.Customer.ProductDetail.ProductDetailViewModel

@Composable
fun AddCommentBottomSheetProduct(
    viewModel: ProductDetailViewModel,
    idUsuario: Int,
    idProducto: Int,
    onDismiss: () -> Unit
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
                        .clickable { viewModel.updateRating(index) }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = commentText,
            onValueChange = { viewModel.updateCommentText(it) },
            placeholder = { Text("Add your comment...") },
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
                    if (commentText.isNotBlank() && rating > 0) {
                        // Actualizamos el estado (opcional, pero puedes hacerlo para asegurar)
                        viewModel.updateCommentText(commentText)
                        viewModel.updateRating(rating)
                        /*val nuevoComentario = ComentarioProducto(
                            id = 0,
                            idUsuario = idUsuario,
                            idProducto = idProducto,
                            text = commentText,
                            rating = rating
                        )*/
                        viewModel.agregarComentario(idUsuario, idProducto)
                        onDismiss()
                    }
                }
            ) {
                Text("Save", color = Color(0xFF8A4F2E))
            }
        }
    }
}
