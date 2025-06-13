package com.Proyecto.coffeepalace.ui.Screens.Seller.AddProduct
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.ui.Screens.Seller.AddProduct.AddProductViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    viewModel: AddProductViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            title = { Text("Add product") },
            navigationIcon = {
                IconButton(onClick = { /* TODO: Volver */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )

        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Name") })
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Price") })
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Category") })
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Description") })

        Button(
            onClick = { /* Agregar imagen */ },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Icon(Icons.Default.AddAPhoto, contentDescription = null)
            Text(" Add image")
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = { /* Guardar producto */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Guardar", color = Color.White)
        }
    }
}
