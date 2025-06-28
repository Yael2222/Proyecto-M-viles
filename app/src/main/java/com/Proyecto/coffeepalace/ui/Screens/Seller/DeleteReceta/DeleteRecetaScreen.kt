// com.Proyecto.coffeepalace.ui.Screens.Seller.Receta/DeleteRecetaScreen.kt
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
import androidx.compose.ui.unit.dp
// import androidx.lifecycle.viewmodel.compose.viewModel // <-- ¡Elimina esta importación!
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.Proyecto.coffeepalace.Data.Model.receta
import com.Proyecto.coffeepalace.Data.Model.ingrediente
import com.Proyecto.coffeepalace.Data.Model.RecetaWithIngredientes // <--- Asegúrate de que esta importación esté
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteRecetaScreen(
    viewModel: DeleteRecetaViewModel, // <-- ¡Recibe el ViewModel como parámetro!
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
                        items(recetasWithIngredientes, key = { it.receta.id ?: 0L }) { item -> // Añade key
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

// RecetaItem se mantiene igual, no necesita cambios ya que recibe los objetos de datos.
@Composable
fun RecetaItem(receta: receta, ingredientes: List<ingrediente>, onDeleteClick: (Long) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
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
            Row(verticalAlignment = Alignment.Top, modifier = Modifier.weight(1f)) {
                // Si la imagen puede ser nula o en blanco, muestra un placeholder o nada
                if (!receta.imagen.isNullOrBlank()) {
                    Image(
                        painter = rememberAsyncImagePainter(model = receta.imagen),
                        contentDescription = "Imagen de ${receta.nombre}",
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                } else {
                    // Opcional: Mostrar un icono o placeholder si no hay imagen
                    // Icon(Icons.Default.Image, contentDescription = "No image", modifier = Modifier.size(80.dp).align(Alignment.CenterVertically))
                    // Spacer(modifier = Modifier.width(16.dp))
                }


                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
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
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Ingredientes:",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Column(
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            ingredientes.forEach { ingrediente ->
                                Text(
                                    text = "• ${ingrediente.nombre}",
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

            IconButton(
                onClick = { onDeleteClick(receta.id ?: 0L) }, // Asegúrate que el ID no sea nulo al pasar
                modifier = Modifier.align(Alignment.Top)
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