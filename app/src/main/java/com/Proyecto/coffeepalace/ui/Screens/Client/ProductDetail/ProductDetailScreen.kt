package com.Proyecto.coffeepalace.ui.Screens.Client.ProductDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.Proyecto.coffeepalace.CoffeePalaceApplication
import com.Proyecto.coffeepalace.Data.Model.comentario_producto
import com.Proyecto.coffeepalace.R
import com.Proyecto.coffeepalace.di.AppContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: Int
) {
    val application = LocalContext.current.applicationContext as CoffeePalaceApplication

    val viewModel: ProductDetailViewModel = viewModel(
        factory = ProductDetailViewModelFactory(AppContainer)
    )

    val product by viewModel.product.collectAsState()
    val comments by viewModel.comments.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val showLoginRequiredDialog by viewModel.showLoginRequiredDialog.collectAsState()

    var showCommentDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(productId) {
        // La carga inicial se maneja en el init del ViewModel
    }

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError() // Limpiar el error después de mostrarlo
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text( "Volver") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (product == null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Producto no encontrado.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Image(
                        painter = rememberAsyncImagePainter(model = product!!.imagen),
                        contentDescription = product!!.nombre,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = product!!.nombre,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "$${String.format("%.2f", product!!.precio)}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = product!!.descripcion,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            viewModel.addProductToCart()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.AddShoppingCart, contentDescription = "Agregar al Carrito")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Agregar al Carrito", color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Comentarios",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = {
                            if (AppContainer.authRepository.isLoggedIn()) {
                                showCommentDialog = true
                            } else {
                                viewModel.showLoginDialog()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Añadir Comentario")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (comments.isEmpty()) {
                    item {
                        Text("Sé el primero en comentar este producto.", style = MaterialTheme.typography.bodyMedium)
                    }
                } else {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                            LazyRow(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(comments) { comment ->
                                    CommentCard(comment = comment)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }

    if (showCommentDialog) {
        AddCommentDialog(
            onDismiss = { showCommentDialog = false },
            onCommentAdded = { text, rating ->
                viewModel.addComment(text, rating)
                showCommentDialog = false
            }
        )
    }

    if (showLoginRequiredDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissLoginRequiredDialog() },
            title = { Text("Inicio de Sesión Requerido") },
            text = { Text("Necesitas iniciar sesión para realizar esta acción. ¿Deseas iniciar sesión ahora?") },
            confirmButton = {
                Button(onClick = {
                    viewModel.dismissLoginRequiredDialog()
                    navController.navigate("login")
                }) {
                    Text("Iniciar Sesión", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissLoginRequiredDialog() }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun CommentCard(comment: comentario_producto) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .height(180.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDEBD9))
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = comment.usuario.imagen,
                        error = painterResource(id = R.drawable.default_avatar),
                        placeholder = painterResource(id = R.drawable.default_avatar)
                    ),
                    contentDescription = "Foto de perfil de ${comment.usuario.nombre}",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = comment.usuario.nombre,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(5) { index ->
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (index < comment.calificacion) Color(0xFFFFD700) else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = comment.texto,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCommentDialog(
    onDismiss: () -> Unit,
    onCommentAdded: (text: String, rating: Int) -> Unit
) {
    var commentText by remember { mutableStateOf("") }
    var commentRating by remember { mutableStateOf(0) }
    var isRatingError by remember { mutableStateOf(false) }
    var isTextError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Añadir Comentario") },
        text = {
            Column {
                OutlinedTextField(
                    value = commentText,
                    onValueChange = {
                        commentText = it
                        isTextError = false
                    },
                    label = { Text("Tu comentario") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    isError = isTextError,
                    supportingText = { if (isTextError) Text("El comentario no puede estar vacío.", color = MaterialTheme.colorScheme.error) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Calificación:", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(5) { index ->
                            IconButton(onClick = {
                                commentRating = index + 1
                                isRatingError = false
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "${index + 1} estrellas",
                                    tint = if (index < commentRating) Color(0xFFFFD700) else Color.Gray,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }
                    if (isRatingError) {
                        Text("Por favor, selecciona una calificación.", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    isTextError = commentText.isBlank()
                    isRatingError = commentRating == 0

                    if (!isTextError && !isRatingError) {
                        onCommentAdded(commentText, commentRating)
                    }
                }
            ) {
                Text("Enviar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}