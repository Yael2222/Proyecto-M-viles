package com.Proyecto.coffeepalace.ui.Screens.Client.RecetaDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
// import androidx.hilt.navigation.compose.hiltViewModel // ELIMINAR ESTA IMPORTACIÓN
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
// Importa tu ViewModel desde la ubicación correcta
import com.Proyecto.coffeepalace.ui.Screens.Client.RecetaDetail.RecetaDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecetaDetailScreen(
    navController: NavController,
    recetaId: Long,
    // CAMBIO CLAVE: El ViewModel ahora se pasa como parámetro y NO se obtiene con hiltViewModel()
    viewModel: RecetaDetailViewModel
) {
    val recetaDetail by viewModel.recetaDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(recetaId) {
        viewModel.setRecetaId(recetaId) // Nuevo método para pasar el ID al ViewModel
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles de la Receta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    Text(
                        text = "Error: $error",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                recetaDetail != null -> {
                    val rec = recetaDetail!!.receta
                    val ingredientes = recetaDetail!!.ingredientes

                    Image(
                        painter = rememberAsyncImagePainter(model = rec.imagen),
                        contentDescription = "Imagen de la receta ${rec.nombre}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .background(Color.LightGray)
                    )

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = rec.nombre,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = rec.descripcion,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Ingredientes:",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (ingredientes.isNullOrEmpty()) {
                            Text("No hay ingredientes listados.", style = MaterialTheme.typography.bodyMedium)
                        } else {
                            ingredientes.forEach { ingrediente ->
                                Text(
                                    text = "• ${ingrediente.nombre}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Instrucciones:",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = rec.instrucciones,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                else -> {
                    Text(
                        text = "Selecciona una receta para ver los detalles.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}