package com.Proyecto.coffeepalace.ui.Screens.AdminAdsPage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete // Icono para eliminar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage // Necesitas la dependencia de Coil
import com.Proyecto.coffeepalace.Data.Model.ads // Tu modelo de datos de anuncios
import com.Proyecto.coffeepalace.ui.theme.BackgroundColor
import com.Proyecto.coffeepalace.ui.theme.LightCoffeeBrown
import com.Proyecto.coffeepalace.ui.theme.CoffeeBrown
import com.Proyecto.coffeepalace.ui.theme.TextWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAdsScreen(
    modifier: Modifier = Modifier,
    viewModel: AdsViewModel = viewModel(), // Usa tu AdsViewModel
    onBackClick: () -> Unit // Para la navegación de regreso
) {
    val adsList by viewModel.advertisements.collectAsState() // Observa la lista de anuncios
    val isLoading by viewModel.isLoading.collectAsState() // Observa el estado de carga

    var showAddAdDialog by remember { mutableStateOf(false) } // Controla la visibilidad del diálogo
    var adTitleInput by remember { mutableStateOf(TextFieldValue("")) }
    var adDescriptionInput by remember { mutableStateOf(TextFieldValue("")) }
    var adImageUrlInput by remember { mutableStateOf(TextFieldValue("")) }

    var showImagePreviewDialog by remember { mutableStateOf(false) }
    var imageToShowUrl by remember { mutableStateOf<String?>(null) }
    var titleToShow by remember { mutableStateOf<String?>(null) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            ) {
                TopAppBar(
                    title = { Text("Ads & Promotions") },
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
            ExtendedFloatingActionButton(
                onClick = {
                    // Reinicia los campos para el nuevo anuncio
                    adTitleInput = TextFieldValue("")
                    adDescriptionInput = TextFieldValue("")
                    adImageUrlInput = TextFieldValue("")
                    showAddAdDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                containerColor = CoffeeBrown,
                contentColor = TextWhite,
                expanded = true,
                text = { Text("New Ad", fontSize = 18.sp) },
                icon = { Icon(Icons.Filled.Add, "Add") }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BackgroundColor)
        ) {
            // Muestra indicador de carga si está cargando
            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = CoffeeBrown)
            }

            // Muestra la lista de anuncios si no hay error y no está cargando (o incluso si está cargando pero ya hay datos)
            if (false) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No ads available. Click '+' to add one.", color = TextWhite)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 80.dp // Espacio para el FAB
                    )
                ) {
                    items(adsList) { ad ->
                        AdCard(
                            ad = ad,
                            onDeleteClick = { viewModel.deleteAds(ad.id) }, // Llama a la función de eliminar del ViewModel
                            onItemClick = {
                                titleToShow = ad.titulo
                                imageToShowUrl = ad.imagen
                                showImagePreviewDialog = true  }
                        )
                    }
                }
            }
        }
    }

    // El AlertDialog para añadir anuncios
    if (showAddAdDialog) {
        AlertDialog(
            onDismissRequest = { showAddAdDialog = false },
            title = { Text("Add New Advertisement") },
            text = {
                Column {
                    OutlinedTextField(
                        value = adTitleInput,
                        onValueChange = { adTitleInput = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CoffeeBrown,
                            unfocusedBorderColor = LightCoffeeBrown,
                            focusedLabelColor = CoffeeBrown,
                            cursorColor = CoffeeBrown,
                            focusedTextColor = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = adDescriptionInput,
                        onValueChange = { adDescriptionInput = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CoffeeBrown,
                            unfocusedBorderColor = LightCoffeeBrown,
                            focusedLabelColor = CoffeeBrown,
                            cursorColor = CoffeeBrown,
                            focusedTextColor = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = adImageUrlInput,
                        onValueChange = { adImageUrlInput = it },
                        label = { Text("Image URL") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CoffeeBrown,
                            unfocusedBorderColor = LightCoffeeBrown,
                            focusedLabelColor = CoffeeBrown,
                            cursorColor = CoffeeBrown,
                            focusedTextColor = Color.Black
                        )
                    )
                    // Considerar un "Upload Image" real aquí si se integra con Supabase Storage
                    // Por ahora, es un campo de texto para la URL de la imagen.
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (adTitleInput.text.isNotBlank() && adDescriptionInput.text.isNotBlank()) {
                            // Llama a la función addAds del ViewModel
                            viewModel.addAds(
                                description = adDescriptionInput.text,
                                image = adImageUrlInput.text, // Pasa la URL de la imagen
                                name = adTitleInput.text // 'name' es el título en tu DAO
                            )
                            showAddAdDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CoffeeBrown, contentColor = TextWhite)
                ) {
                    Text("Add Advertisement")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showAddAdDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = LightCoffeeBrown, contentColor = TextWhite)
                ) {
                    Text("Cancel")
                }
            },
            containerColor = BackgroundColor
        )
    }

    if (showImagePreviewDialog && imageToShowUrl != null) {
        AlertDialog(
            onDismissRequest = {
                showImagePreviewDialog = false
                imageToShowUrl = null // Limpia la URL al cerrar
                titleToShow = null
            },
            title = { Text("Ad Preview", color = CoffeeBrown) },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Muestra la imagen con AsyncImage
                    Text(text = "Titulo: $titleToShow", fontSize = 16.sp, color = Color.DarkGray)
                    AsyncImage(
                        model = imageToShowUrl,
                        contentDescription = "Full-size Ad Image",
                        contentScale = ContentScale.Fit, // Ajusta la imagen dentro de los límites del diálogo
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp) // Limita la altura para que no ocupe toda la pantalla
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray) // Fondo mientras carga
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    //Text(text = "Description: ${ad.descripcion}", fontSize = 14.sp, color = Color.Gray)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showImagePreviewDialog = false
                        imageToShowUrl = null
                        titleToShow = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CoffeeBrown, contentColor = TextWhite)
                ) {
                    Text("Close")
                }
            },
            containerColor = BackgroundColor // Usamos el mismo color de fondo
        )
    }
}

// Componente individual de la tarjeta de anuncio
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdCard(
    ad: ads,
    onDeleteClick: () -> Unit,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp) // Ajusta la altura según tus necesidades
            .clickable(onClick = onItemClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightCoffeeBrown,
            contentColor = TextWhite
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f) // Esto permite que el texto ocupe el espacio restante
            ) {
                Text(
                    text = ad.titulo, // Muestra el título
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite // Asegúrate de que el color del texto sea visible
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = ad.descripcion, // Muestra la descripción
                    fontSize = 14.sp,
                    color = TextWhite.copy(alpha = 0.7f) // Un poco más tenue
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            // Muestra la imagen usando Coil
            AsyncImage(
                model = ad.imagen, // URL de la imagen
                contentDescription = "Ad Image",
                contentScale = ContentScale.Crop, // Escala la imagen para que llene el área
                modifier = Modifier
                    .size(80.dp) // Tamaño de la imagen
                    .clip(RoundedCornerShape(8.dp)) // Esquinas redondeadas para la imagen
                    .background(Color.Gray) // Fondo temporal mientras carga la imagen
            )

            // Botón de eliminar
            IconButton(onClick = onDeleteClick) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Delete",
                    tint = TextWhite // Color del icono
                )
            }
        }
    }
}