package com.Proyecto.coffeepalace.ui.Screens.Seller.Category

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.ui.theme.BrownCoffee

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(viewModel: CategoryViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var newCategory by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TopAppBar(
            title = { Text("Categories") },
            navigationIcon = {
                IconButton(onClick = { /* Navigate back */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )

        viewModel.categories.forEachIndexed { index, category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(category)
                TextButton(onClick = {
                    // lógica para editar
                }) {
                    Text("Edit")
                }
            }
        }

        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BrownCoffee,
                contentColor = Color.White)
        ) {
            Text("+ Add category")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        if (newCategory.isNotBlank()) {
                            viewModel.addCategory(newCategory)
                            newCategory = ""
                            showDialog = false
                        }
                    }) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrownCoffee,
                            contentColor = Color.White) )
                    {
                        Text("Cancel")
                    }
                },
                title = { Text("New Category") },
                text = {
                    OutlinedTextField(
                        value = newCategory,
                        onValueChange = { newCategory = it },
                        label = { Text("Category Name") }
                    )
                }
            )
        }
    }
}
