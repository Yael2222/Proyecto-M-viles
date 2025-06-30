package com.Proyecto.coffeepalace.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.shape.CircleShape

@Composable
fun BottomBar(
    currentRoute: String,
    onHomeClick: () -> Unit,
    onCategoryClick: () -> Unit,
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit,
    onCartClick: () -> Unit
) {
    Box {
        NavigationBar(containerColor = Color.White, contentColor = Color(0xFF8A4F2E)) {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text("Home", fontSize = 10.sp) },
                selected = currentRoute == "home",
                onClick = onHomeClick,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6D4C41),
                    selectedTextColor = Color(0xFF6D4C41),
                    unselectedIconColor = Color(0xFF9E9E9E),
                    unselectedTextColor = Color(0xFF9E9E9E)
                )
            )

            NavigationBarItem(
                icon = { Icon(Icons.Default.Category, contentDescription = "Category") },
                label = { Text("Category", fontSize = 10.sp) },
                selected = currentRoute == "category",
                onClick = onCategoryClick,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6D4C41),
                    selectedTextColor = Color(0xFF6D4C41),
                    unselectedIconColor = Color(0xFF9E9E9E),
                    unselectedTextColor = Color(0xFF9E9E9E)
                )
            )

            Spacer(modifier = Modifier.width(64.dp))

            NavigationBarItem(
                icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                label = { Text("Search", fontSize = 10.sp) },
                selected = currentRoute == "search",
                onClick = onSearchClick,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6D4C41),
                    selectedTextColor = Color(0xFF6D4C41),
                    unselectedIconColor = Color(0xFF9E9E9E),
                    unselectedTextColor = Color(0xFF9E9E9E)
                )
            )

            NavigationBarItem(
                icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                label = { Text("Profile", fontSize = 10.sp) },
                selected = currentRoute == "profile",
                onClick = onProfileClick,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6D4C41),
                    selectedTextColor = Color(0xFF6D4C41),
                    unselectedIconColor = Color(0xFF9E9E9E),
                    unselectedTextColor = Color(0xFF9E9E9E),
                )
            )
        }

        // Botón flotante central
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            FloatingActionButton(
                containerColor = Color(0xFF8A4F2E),
                shape = CircleShape,
                onClick = onCartClick,
                modifier = Modifier
                    .size(64.dp)
                    .shadow(10.dp, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = Color.White
                )
            }
        }
    }
}
