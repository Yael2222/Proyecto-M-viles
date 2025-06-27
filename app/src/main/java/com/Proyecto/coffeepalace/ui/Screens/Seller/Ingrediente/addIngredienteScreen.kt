package com.Proyecto.coffeepalace.ui.Screens.Seller.Ingrediente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.Proyecto.coffeepalace.ui.theme.BrownCoffee
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addIngredienteScreen(
    viewModel: addIngredienteViewModel,
    navController: NavHostController
) {
    val ingredientes by viewModel.ingredientes.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var newIngrediente by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ingredientes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            LazyColumn {
                items(ingredientes) { ingrediente ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = ingrediente.nombre)
                        IconButton(onClick = {
                            coroutineScope.launch {
                                ingrediente.id.let {
                                    val deleted = viewModel.deleteIngrediente(it)
                                    // Nada extra aquí, el ViewModel ya recarga la lista
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Ingrediente"
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrownCoffee,
                    contentColor = Color.White
                )
            ) {
                Text("+ Añadir Ingrediente")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "* Si no se puede borrar, es porque está asignado a una receta. Borrar primero la receta",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Red
            )

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Nuevo Ingrediente") },
                    text = {
                        OutlinedTextField(
                            value = newIngrediente,
                            onValueChange = { newIngrediente = it },
                            label = { Text("Nombre del ingrediente") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            if (newIngrediente.isNotBlank()) {
                                coroutineScope.launch {
                                    viewModel.addIngrediente(newIngrediente.trim())
                                    newIngrediente = ""
                                    showDialog = false
                                }
                            }
                        }) {
                            Text("Agregar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDialog = false
                        }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}
