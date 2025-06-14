package com.Proyecto.coffeepalace.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.R
import com.Proyecto.coffeepalace.ui.theme.Brown
import com.Proyecto.coffeepalace.ui.theme.LightGray200

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeePalaceScaffold(
    content: @Composable (PaddingValues) -> Unit,
    title: String = "The Coffee Palace",
    showBottomBar: Boolean = true,
    showTopBar: Boolean = true,
    floatingActionButton: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {}
) {
    Scaffold(
        topBar = {
            if (showTopBar) {
                CoffeePalaceTopAppBar(
                    title = title,
                    onProfileClick = {},
                    onLogoClick = {}
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                CoffeePalaceBottomAppBar()
            }
        },
        floatingActionButton = floatingActionButton,
        snackbarHost = snackbarHost
    ) { innerPadding ->
        content(innerPadding)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeePalaceTopAppBar(
    title: String = "The Coffee Palace",
    showProfileIcon: Boolean = true,
    onProfileClick: () -> Unit = {},
    onLogoClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onLogoClick,
                ) {
                    Image(
                        modifier = Modifier.width(85.dp).height(80.dp),
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "Coffee Palace Logo",
                    )
                }
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4E342E)
                )
            }
        },
        actions = {
            if (showProfileIcon) {
                IconButton(onClick = onProfileClick) {
                    Image(
                        modifier = Modifier.size(80.dp),
                        painter = painterResource(R.drawable.profile_icon),
                        contentDescription = "User Profile",
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LightGray200,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun CoffeePalaceBottomAppBar() {
    NavigationBar(
        containerColor = LightGray200
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Brown) },
            label = { Text("Home", color = Brown) },
            selected = true,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.icon_sell_outlined),
                    contentDescription = "Category",
                    tint = Brown
                )
            },
            label = { Text("Category", color = Brown) },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "ShoppingCart",
                    tint = Brown
                )
            },
            label = { Text("Cart", color = Brown) },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Brown) },
            label = { Text("Search", color = Brown) },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile", tint = Brown) },
            label = { Text("Profile", color = Brown) },
            selected = false,
            onClick = {}
        )
    }
}

@Composable
fun CoffeePalaceHomeScreen() {
    CoffeePalaceScaffold(
        title = "The Coffee Palace",
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Your custom content here
                Text(
                    "Welcome to The Coffee Palace!",
                    style = MaterialTheme.typography.headlineMedium
                )
//                FeaturesLazyRow(items = featureItems.filter { it.category == "Drinks" })
            }
        }
    )
}

@Composable
fun CoffeePalaceDetailScreen() {
    CoffeePalaceScaffold(
        title = "Product Details",
        showBottomBar = false,
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
            }
        }
    )
}