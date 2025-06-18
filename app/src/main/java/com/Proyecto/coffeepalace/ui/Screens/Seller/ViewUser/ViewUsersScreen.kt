package com.Proyecto.coffeepalace.ui.Screens.Seller.ViewUser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewUsersScreen(modifier: Modifier = Modifier,
                    viewModel: ViewUsersViewModel,
                    onSelectUser: (UserModel) -> Unit) {
    val search by viewModel.searchQuery
    val filteredUsers = viewModel.filterUsers()

    Column {
        TopAppBar(
            title = { Text("View Users") },
            navigationIcon = {
                IconButton(onClick = { /* navegación atrás */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )

        OutlinedTextField(
            value = search,
            onValueChange = { viewModel.searchQuery.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = { Text("Buscar clientes") }
        )

        LazyColumn {
            items(filteredUsers) { user ->
                ListItem(
                    headlineContent = { Text(user.name) },
                    supportingContent = { Text(user.email) },
                    trailingContent = {
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.selectUser(user)
                            onSelectUser(user)
                        }
                )
            }
        }
    }
}

