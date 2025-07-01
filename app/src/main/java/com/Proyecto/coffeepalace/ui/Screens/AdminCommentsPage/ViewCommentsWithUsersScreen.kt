package com.Proyecto.coffeepalace.ui.Screens.AdminCommentsPage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.lifecycle.viewmodel.compose.viewModel
import com.Proyecto.coffeepalace.Data.Model.ComentarioConNombreUsuario
import com.Proyecto.coffeepalace.ui.theme.CoffeeBrown
import com.Proyecto.coffeepalace.ui.theme.TextWhite
import com.Proyecto.coffeepalace.ui.theme.LightCoffeeBrown
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewCommentsWithUsersScreen(
    modifier: Modifier = Modifier,
    viewModel: ViewCommentsWithUsersViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val comentariosConUsuario by viewModel.comentariosConUsuario.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            ) {
                TopAppBar(
                    title = { Text("Comentarios") },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = CoffeeBrown,
                        titleContentColor = TextWhite,
                        navigationIconContentColor = TextWhite
                    )
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = CoffeeBrown
            ) {
                Icon(Icons.Default.FilterList, contentDescription = "Filtrar")
            }
        }
    ) { paddingValues ->

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        "Filtrar por calificación",
                        color = Color.White
                    )
                },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(LightCoffeeBrown)
                            .padding(8.dp)
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 5.dp),
                            thickness = 1.dp,
                            color = Color.White.copy(alpha = 0.5f)
                        )
                        (1..5).forEach { calificacion ->
                            TextButton(
                                onClick = {
                                    viewModel.setFiltroCalificacion(calificacion)
                                    showDialog = false
                                },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = Color.White
                                )
                            ) {
                                Text("⭐ $calificacion estrellas")
                            }
                        }
                        TextButton(
                            onClick = {
                                viewModel.setFiltroCalificacion(null)
                                showDialog = false
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color.White
                            )
                        ) {
                            Text("Mostrar todos")
                        }
                    }
                },
                confirmButton = {}, // vacío, porque usamos solo el texto
                containerColor = LightCoffeeBrown // <-- solo funciona en versiones recientes de Compose
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                error != null -> Text(
                    text = "Error: $error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
                comentariosConUsuario.isEmpty() -> Text(
                    text = "No hay comentarios.",
                    modifier = Modifier.align(Alignment.Center)
                )
                else -> LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(comentariosConUsuario) { item ->
                        CommentWithUserCard(item)
                    }
                }
            }
        }
    }
}

@Composable
fun CommentWithUserCard(item: ComentarioConNombreUsuario) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Autor: ${item.nombreUsuario}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Producto ID: ${item.comentario.id_producto} - Producto: ${item.nombreProducto}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Calificación:",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.width(4.dp))
                repeat(5) { index ->
                    if (index < item.comentario.calificacion) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.StarBorder,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Comentario:",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = item.comentario.texto,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
