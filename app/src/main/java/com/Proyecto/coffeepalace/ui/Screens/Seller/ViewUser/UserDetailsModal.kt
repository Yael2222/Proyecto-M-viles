package com.Proyecto.coffeepalace.ui.Screens.Seller.ViewUser

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.Proyecto.coffeepalace.ui.theme.BrownCoffee


@Composable
fun UserDetailsModal(user: UserModel, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrownCoffee,
            contentColor = MaterialTheme.colorScheme.onPrimary
            )) {
                Text("Cerrar")
            }
        },
        title = { Text("Detalles del Cliente") },
        text = {
            Column {
                Text("Nombre: ${user.name}")
                Text("Email: ${user.email}")
                Text("Última visita: ${user.lastVisit}")
                Text("Total de compras: ${user.purchases}")
            }
        }
    )
}

