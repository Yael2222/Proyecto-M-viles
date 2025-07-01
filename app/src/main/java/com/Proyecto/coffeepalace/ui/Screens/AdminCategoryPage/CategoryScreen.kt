package com.Proyecto.coffeepalace.ui.Screens.AdminCategoryPage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.Proyecto.coffeepalace.Data.Model.categoria
import com.Proyecto.coffeepalace.ui.theme.BackgroundColor
import com.Proyecto.coffeepalace.ui.theme.LightCoffeeBrown
import com.Proyecto.coffeepalace.ui.theme.CoffeeBrown
import com.Proyecto.coffeepalace.ui.theme.TextWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val categories by viewModel.categories.collectAsState()

    var newCategory by remember { mutableStateOf("") }
    var showCategoryDialog by remember { mutableStateOf(false) }
    var currentCategoriaToEdit by remember { mutableStateOf<categoria?>(null) }
    var categoryNameInput by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            ){
                TopAppBar(
                    title = { Text("Categories") },
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
                    categoryNameInput = TextFieldValue("")
                    showCategoryDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                containerColor = CoffeeBrown,
                contentColor = TextWhite,
                expanded = true,
                text = { Text("Add category", fontSize = 18.sp) },
                icon = { Icon(Icons.Filled.Add, "Add") }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BackgroundColor),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 80.dp
            )
        ) {
            items(categories) { categoriaItem ->
                CategoryCard(
                    categoria = categoriaItem,
                    onDeleteClick = {
                        viewModel.deleteCategory(categoriaItem.id)
                    },
                    onItemClick = { }
                )
            }
        }
    }

    if (showCategoryDialog) {
        AlertDialog(
            onDismissRequest = { showCategoryDialog = false },
            title = {
                Text("Add New Category")
            },
            text = {
                OutlinedTextField(
                    value = categoryNameInput,
                    onValueChange = { categoryNameInput = it },
                    label = { Text("Category Name") },
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
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (categoryNameInput.text.isNotBlank()) {
                            viewModel.addCategory(categoryNameInput.text)
                        }
                        showCategoryDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CoffeeBrown, contentColor = TextWhite)
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showCategoryDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = LightCoffeeBrown, contentColor = TextWhite)
                ) {
                    Text("Cancel")
                }
            },
            containerColor = BackgroundColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(
    categoria: categoria,
    onDeleteClick: () -> Unit,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
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
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = categoria.nombre,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = onDeleteClick) {
                    Text("Delete", color = TextWhite)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Delete",
                    tint = TextWhite,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = onDeleteClick)
                )
            }
        }
    }
}