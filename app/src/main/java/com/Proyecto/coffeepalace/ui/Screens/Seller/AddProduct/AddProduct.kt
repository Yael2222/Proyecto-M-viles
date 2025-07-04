package com.Proyecto.coffeepalace.ui.Screens.Seller.AddProduct

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    viewModel: AddProductViewModel,
    navController: NavHostController
) {

    val name by viewModel.name.collectAsState()
    val price by viewModel.price.collectAsState()
    val description by viewModel.description.collectAsState()
    val selectedCategoryId by viewModel.selectedCategoryId.collectAsState()
    val categorias by viewModel.categorias.collectAsState()
    val saveSuccess by viewModel.saveSuccess.collectAsState()
    val uploadingImage by viewModel.uploadingImage.collectAsState() // <--- Nuevo estado

    var showSavedDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) } // La URI local que Coil previsualiza

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri // Almacenar la URI local para previsualizar
            // No se asigna a viewModel.imageUri.value AQUI, se hará después de la subida.
        }
    }

    if (saveSuccess) {
        LaunchedEffect(saveSuccess) {
            showSavedDialog = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir Producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { viewModel.name.value = it },
                label = { Text("Nombre del producto") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = price,
                onValueChange = {
                    if (it.matches(Regex("^\\d*\\.?\\d*\$"))) {
                        viewModel.price.value = it
                    }
                },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = categorias.find { it.id == selectedCategoryId }?.nombre ?: "Selecciona la categoria",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoria") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categorias.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat.nombre) },
                            onClick = {
                                viewModel.selectedCategoryId.value = cat.id
                                expanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = description,
                onValueChange = { viewModel.description.value = it },
                label = { Text("Descripcion") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(Icons.Default.AddAPhoto, contentDescription = null)
                Text(" Añadir Imagen")
            }

            // Previsualización de la imagen seleccionada localmente
            selectedImageUri?.let { uri ->
                val bitmap = remember(uri) {
                    if (Build.VERSION.SDK_INT < 28) {
                        android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, uri)
                        ImageDecoder.decodeBitmap(source)
                    }
                }
                Image(bitmap = bitmap.asImageBitmap(), contentDescription = null, modifier = Modifier.size(100.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // ¡CAMBIO AQUÍ! Ahora llama a uploadAndSaveProduct
                    scope.launch {
                        viewModel.uploadAndSaveProduct(context, selectedImageUri)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uploadingImage // Deshabilitar el botón mientras se sube la imagen
            ) {
                if (uploadingImage) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Subiendo Imagen...")
                } else {
                    Text("Añadir producto" )
                }
            }
        }

        if (showSavedDialog) {
            AlertDialog(
                onDismissRequest = { showSavedDialog = false },
                title = { Text("Producto añadido") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.resetForm()
                        selectedImageUri = null // También limpiar la URI local de previsualización
                        showSavedDialog = false
                    }) {
                        Text("OK")
                    }
                },
                properties = DialogProperties(dismissOnClickOutside = false)
            )
        }
    }
}