package com.Proyecto.coffeepalace.ui.Screens.Seller.Receta

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.foundation.gestures.animateScrollBy // Necesario para animateScrollBy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecetaScreen(
    viewModel: AddRecetaViewModel,
    navController: NavHostController
) {
    val nombre by viewModel.nombre
    val descripcion by viewModel.descripcion
    val imagen by viewModel.imagen
    val instrucciones by viewModel.instrucciones
    val ingredientesDisponibles = viewModel.ingredientesDisponibles
    val ingredientesSeleccionados = viewModel.ingredientesSeleccionados
    val mensaje by viewModel.mensaje

    var showSavedDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            viewModel.imagen.value = uri.toString()
        }
    }

    LaunchedEffect(mensaje) {
        if (mensaje.contains("agregada", ignoreCase = true)) {
            showSavedDialog = true
        }
    }

    if (showSavedDialog) {
        AlertDialog(
            onDismissRequest = { showSavedDialog = false },
            title = { Text("Receta agregada") },
            text = { Text("La receta se añadió correctamente.") },
            confirmButton = {
                TextButton(onClick = {
                    showSavedDialog = false
                    viewModel.limpiarFormulario()
                    navController.popBackStack()
                }) {
                    Text("OK")
                }
            },
            properties = DialogProperties(dismissOnClickOutside = false)
        )
    }

    val scrollState = rememberScrollState()


    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // El padre no consume nada en la fase previa, dejando que los hijos lo hagan primero.
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset, // Lo que el hijo ya consumió
                available: Offset, // Lo que el hijo NO consumió y está disponible para el padre
                source: NestedScrollSource
            ): Offset {
                // Si el hijo NO consumió todo el scroll disponible, el padre intenta consumirlo.
                // Esto es crucial: el padre solo se desplaza si el hijo ya no puede.
                val delta = available.y
                val scrolled = scrollState.dispatchRawDelta(-delta)
                return Offset(x = 0f, y = -scrolled)
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                // El padre no consume el fling en la fase previa, dejando que los hijos lo hagan primero.
                return Velocity.Zero
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                // Si el hijo NO consumió todo el fling disponible, el padre intenta consumirlo.
                val delta = available.y
                if (delta != 0f) {
                    scrollState.animateScrollBy(-delta) // animateScrollBy es suspend.
                    return available // El padre consume todo el fling restante
                }
                return Velocity.Zero
            }
        }
    }


    Scaffold(
        // Aplicar nestedScroll al Scaffold para que coordine los scrolls.
        // Esto permite que el desplazamiento de los hijos se propague al padre.
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("Agregar Receta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding) // Aplicar padding del Scaffold aquí
                .padding(horizontal = 16.dp) // Añadir padding horizontal
                .verticalScroll(scrollState) // Hacer la columna principal desplazable
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { viewModel.nombre.value = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { viewModel.descripcion.value = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = instrucciones,
                onValueChange = { viewModel.instrucciones.value = it },
                label = { Text("Instrucciones") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(Icons.Default.AddAPhoto, contentDescription = "Seleccionar imagen")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Añadir Imagen")
            }

            selectedImageUri?.let { uri ->
                val bitmap = remember(uri) {
                    if (Build.VERSION.SDK_INT < 28) {
                        android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, uri)
                        ImageDecoder.decodeBitmap(source)
                    }
                }
                Image(bitmap = bitmap.asImageBitmap(), contentDescription = null, modifier = Modifier.size(100.dp).padding(top = 8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Seleccionar Ingredientes", style = MaterialTheme.typography.titleMedium)

            // LazyColumn para ingredientes
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    // ESENCIAL: Limitar la altura de LazyColumn
                    // Si no tiene una altura limitada, intentará tomar TODO el espacio disponible,
                    // lo que puede impedir que el verticalScroll del padre funcione correctamente
                    // o que el botón de abajo se vea.
                    // Si tienes muchos ingredientes, 200.dp puede ser poco.
                    // Ajusta este valor según la cantidad esperada de ingredientes
                    // para que la LazyColumn tenga su propio scroll si es necesario.
                    .heightIn(max = 200.dp)
                    .padding(vertical = 8.dp)
            ) {
                items(ingredientesDisponibles) { ingrediente ->
                    val seleccionado = ingredientesSeleccionados.contains(ingrediente)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.alternarSeleccion(ingrediente) }
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(ingrediente.nombre)
                        if (seleccionado) Text("✔️")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        viewModel.agregarReceta()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Receta")
            }

            if (mensaje.isNotBlank() && !mensaje.contains("agregada", ignoreCase = true)) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(mensaje)
            }
            Spacer(modifier = Modifier.height(16.dp)) // Espacio al final para asegurar el scroll completo
        }
    }
}