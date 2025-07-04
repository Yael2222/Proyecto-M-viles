package com.Proyecto.coffeepalace.ui.Screens.Client.HomePage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage // Importante para cargar desde assets
import com.Proyecto.coffeepalace.Data.Model.producto // Asegúrate de que esta importación sea correcta (antes era 'producto' con 'p' minúscula)
import com.Proyecto.coffeepalace.di.AppContainer // Asegúrate de que esta importación sea correcta
import com.Proyecto.coffeepalace.ui.Screens.User.UserViewModel
import com.Proyecto.coffeepalace.ui.Screens.User.UserViewModel.UserViewModelFactory
import com.Proyecto.coffeepalace.ui.components.AppTopBar
import com.Proyecto.coffeepalace.ui.components.AppBottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    // Asegúrate de que HomeViewModelFactory y los repositorios estén configurados correctamente
    // Si usas Hilt, la inyección del ViewModel sería diferente (ej: viewModel<HomeViewModel>())
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(
            AppContainer.productRepository, // Asumiendo que es productosRepository, no productRepository
            AppContainer.categoryRepository // Asumiendo que es categoriasRepository, no categoryRepository
        )
    )
) {
    val categories by viewModel.categories.collectAsState()
    val productsByCategory by viewModel.productsByCategory.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(
            AppContainer.authRepository, AppContainer.userRepository,
            AppContainer.orderRepository
        )
    )

    Scaffold(
        topBar = { AppTopBar(navController = navController, userViewModel = userViewModel) },
        bottomBar = { AppBottomNavigationBar(navController) }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (errorMessage != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error: ${errorMessage}", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.refreshData() }) {
                        Text("Reintentar")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    // Banner de saludo/eslogan
                    CoffeeSloganBanner()
                }

                // Iterar a través de categorías y mostrar productos
                categories.forEach { category ->
                    val productsForCategory = productsByCategory[category.id] ?: emptyList()
                    if (productsForCategory.isNotEmpty()) {
                        item {
                            CategorySection(
                                categoryName = category.nombre,
                                products = productsForCategory,
                                navController = navController // Pasar navController
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp)) // Espacio al final
                }
            }
        }
    }
}

@Composable
fun CoffeeSloganBanner() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¡Despierta tus sentidos!",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tu momento perfecto está en The Coffee Palace",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CategorySection(categoryName: String, products: List<producto>, navController: NavController) { // Recibir navController
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
            color = Color(0xFF000000),
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { product ->
                ProductCard(product = product, navController = navController) // Pasar navController a ProductCard
            }
        }
    }
}

@Composable
fun ProductCard(product: producto, navController: NavController) { // Recibir navController
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(210.dp)
            // Hacer la tarjeta clickeable y navegar al detalle del producto
            .clickable { navController.navigate("product_detail/${product.id}") }, // <--- CAMBIO CLAVE AQUÍ
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFDACEBB))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = product.imagen, // Asumiendo que tu modelo 'Producto' tiene 'imagen_url'
                contentDescription = product.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.nombre,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color(0xFF000000),
            )
            Text(
                text = product.descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF000000),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${String.format("%.2f", product.precio)}", // Formato para 2 decimales
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF030303),
            )
        }
    }
}