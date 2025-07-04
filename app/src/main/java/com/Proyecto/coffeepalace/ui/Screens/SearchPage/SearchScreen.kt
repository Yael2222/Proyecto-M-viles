package com.Proyecto.coffeepalace.ui.Screens.Search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.ui.text.input.ImeAction
import com.Proyecto.coffeepalace.di.AppContainer
import com.Proyecto.coffeepalace.ui.navigations.Screens
import com.Proyecto.coffeepalace.ui.components.AppTopBar
import com.Proyecto.coffeepalace.ui.components.AppBottomNavigationBar
import com.Proyecto.coffeepalace.ui.Screens.User.UserViewModel
import com.Proyecto.coffeepalace.ui.Screens.User.UserViewModel.UserViewModelFactory
import kotlinx.coroutines.flow.debounce // ¡Importar debounce!
import kotlinx.coroutines.flow.distinctUntilChanged // Para optimizar
import kotlinx.coroutines.flow.filter // Para evitar búsquedas vacías

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(
            AppContainer.productRepository,
            AppContainer.recetaRepository,
            AppContainer.ingredienteRepository,
            AppContainer.categoryRepository
        )
    ),
    userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(
            AppContainer.authRepository,
            AppContainer.userRepository,
            AppContainer.orderRepository
        )
    )
) {
    val searchQuery by searchViewModel.searchQuery.collectAsState()
    val searchResults by searchViewModel.searchResults.collectAsState()
    val isLoading by searchViewModel.isLoading.collectAsState()
    val snackbarMessage by searchViewModel.snackbarMessage.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            searchViewModel.clearSnackbarMessage()
        }
    }

    // NUEVO: LaunchedEffect para la búsqueda en vivo con debounce
    LaunchedEffect(searchViewModel) {
        snapshotFlow { searchQuery } // Convierte el State<TextFieldValue> en un Flow
            .debounce(300L) // Espera 300 ms después de la última pulsación
            .distinctUntilChanged() // Solo emite si el valor realmente cambió
            .filter { it.text.isNotBlank() || it.text.isEmpty() } // Permite búsqueda en blanco para resetear o si hay texto
            .collect { query ->
                // Solo realizamos la búsqueda si el query no está vacío
                // O si queremos que una query vacía muestre todos los resultados (menos común)
                if (query.text.isNotBlank()) {
                    searchViewModel.performSearch()
                } else {
                    // Si el campo de búsqueda está vacío, puedes querer limpiar los resultados
                    searchViewModel.clearResults() // NECESITAS CREAR ESTA FUNCIÓN EN TU ViewModel
                }
            }
    }


    Scaffold(
        topBar = { AppTopBar(navController = navController, userViewModel = userViewModel) },
        bottomBar = { AppBottomNavigationBar(navController = navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Barra de Búsqueda
            OutlinedTextField(
                value = searchQuery.text,
                onValueChange = { newValue ->
                    // Llamamos a onSearchQueryChanged con el nuevo valor
                    searchViewModel.onSearchQueryChanged(TextFieldValue(newValue))
                    // La llamada a performSearch ahora se manejará en el LaunchedEffect de debounce
                },
                label = { Text("Buscar...") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
                trailingIcon = {
                    if (searchQuery.text.isNotBlank()) {
                        IconButton(onClick = {
                            searchViewModel.onSearchQueryChanged(TextFieldValue(""))
                            // Al limpiar, también queremos que se dispare la búsqueda vacía si es necesario
                        }) {
                            Icon(Icons.Filled.Close, contentDescription = "Limpiar")
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        // Aunque el debounce lo maneja, puedes mantener esto para una acción explícita
                        searchViewModel.performSearch()
                    }
                )
            )

            // Eliminado: El botón "Buscar" ya no es necesario
            // Spacer(modifier = Modifier.height(16.dp))
            // Button(...)
            // Spacer(modifier = Modifier.height(16.dp))


            // Resultados de la Búsqueda
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                if (searchResults.products.isNotEmpty() || searchResults.recipes.isNotEmpty()) {
                    Text("Resultados:", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(searchResults.products) { product ->
                            SearchResultItem(
                                name = product.nombre,
                                imageUrl = product.imagen,
                                type = "Producto",
                                onClick = {
                                    navController.navigate(Screens.ProductDetail.createRoute(product.id?.toInt() ?: 0))
                                }
                            )
                        }
                        items(searchResults.recipes) { recipe ->
                            SearchResultItem(
                                name = recipe.nombre,
                                imageUrl = recipe.imagen,
                                type = "Receta",
                                onClick = {
                                    recipe.id?.let {
                                        navController.navigate(Screens.RecetaDetail.createRoute(it.toLong()))
                                    } ?: Log.e("SearchScreen", "Recipe ID is null for navigation")
                                }
                            )
                        }
                    }
                } else {
                    Text("No hay resultados.", modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}

@Composable
fun SearchResultItem(name: String, imageUrl: String, type: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(type, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}