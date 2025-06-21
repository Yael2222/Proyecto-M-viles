package com.Proyecto.coffeepalace.ui.Screens.Seller.Receta

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.rememberAsyncImagePainter
import com.Proyecto.coffeepalace.Data.Model.receta
import com.Proyecto.coffeepalace.Data.Model.ingrediente
import com.Proyecto.coffeepalace.Data.Model.RecetaWithIngredientes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteRecetaScreen(
    viewModel: DeleteRecetaViewModel = viewModel(),
    navController: NavHostController
) {
    val recetasWithIngredientes by viewModel.recetasWithIngredientes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(mensaje) {
        mensaje?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                viewModel.clearMessage()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Eliminar Receta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                recetasWithIngredientes.isEmpty() && !isLoading -> {
                    Text(
                        text = "No hay recetas para eliminar.",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(recetasWithIngredientes) { item ->
                            RecetaItem(
                                receta = item.receta,
                                ingredientes = item.ingredientes,
                                onDeleteClick = {
                                    viewModel.deleteReceta(item.receta.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecetaItem(receta: receta, ingredientes: List<ingrediente>, onDeleteClick: (Long) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(), // ¡Eliminado .heightIn(min = 100.dp) para altura flexible!
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Contenido de la receta (imagen, nombre, descripción)
            Row(verticalAlignment = Alignment.Top, modifier = Modifier.weight(1f)) { // Cambiado a Alignment.Top
                Image(
                    painter = rememberAsyncImagePainter(model = receta.imagen),
                    contentDescription = "Imagen de ${receta.nombre}",
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterVertically),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp) // Espacio entre elementos de la columna
                ) {
                    Text(
                        text = receta.nombre,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = receta.descripcion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    if (ingredientes.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp)) // Espacio antes de los ingredientes
                        Text(
                            text = "Ingredientes:",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Column( // Nueva columna para los ingredientes
                            modifier = Modifier.padding(start = 8.dp) // Sangría para los ingredientes
                        ) {
                            ingredientes.forEach { ingrediente ->
                                Text(
                                    text = "• ${ingrediente.nombre}", // Punto para cada ingrediente
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Ingredientes: N/A",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Botón de eliminar
            IconButton(
                onClick = { onDeleteClick(receta.id) },
                modifier = Modifier.align(Alignment.Top) // Alinea el botón en la parte superior de la tarjeta
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar Receta",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
