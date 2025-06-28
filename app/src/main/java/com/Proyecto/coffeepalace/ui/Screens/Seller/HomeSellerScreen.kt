package com.Proyecto.coffeepalace.ui.Screens.Seller.HomeSeller

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.Proyecto.coffeepalace.ui.navigations.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSellerScreen(
    viewModel: HomeSellerViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Welcome,\nVendendor!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30.sp
                )
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF8B4513).copy(alpha = 0.2f))
                        .padding(8.dp),
                    tint = Color(0xFF8B4513)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))




            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    OptionItem(icon = Icons.Default.People, text = "Ver usuarios") {
                        navController.navigate(Screens.ViewUsers.route)
                    }
                    OptionItem(icon = Icons.Default.ShoppingCart, text = "Ver ordenes") {
                        navController.navigate(Screens.Orders.route)
                    }
                    OptionItem(icon = Icons.Default.Category, text = "Añadir categoría") {
                        navController.navigate(Screens.Category.route)
                    }
                    OptionItem(icon = Icons.Default.LocalCafe, text = "Añadir producto") {
                        navController.navigate(Screens.AddProduct.route)
                    }
                    OptionItem(icon = Icons.Default.RestaurantMenu, text = "Añadir ingrediente") {
                        navController.navigate(Screens.Ingrediente.route)
                    }
                    OptionItem(icon = Icons.Default.MenuBook, text = "Añadir Receta") {
                        navController.navigate(Screens.AddReceta.route)
                    }
                    OptionItem(icon = Icons.Default.Delete, text = "Eliminar Productos") {
                        navController.navigate(Screens.DeleteProduct.route)
                    }
                    OptionItem(icon = Icons.Default.DeleteForever, text = "Eliminar Recetas") {
                        navController.navigate(Screens.DeleteReceta.route)
                    }
                    OptionItem(icon = Icons.Default.Logout, text = "Cerrar Sesion") {
                        navController.navigate(Screens.Category.route)
                    }
                }

            }
            }
        }
    }


@Composable
fun InfoCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5DC))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(title, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            Text(value, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
        }
    }
}

@Composable
fun OptionItem(icon: ImageVector, text: String, onClick: () -> Unit) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF8B4513)
            )
        },
        headlineContent = {
            Text(text, style = MaterialTheme.typography.bodyLarge)
        }
    )
}
