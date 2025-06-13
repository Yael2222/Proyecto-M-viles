package com.Proyecto.coffeepalace.ui.Screens.CategoryPage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.Proyecto.coffeepalace.Data.Model.Category
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

    var showCategoryDialog by remember { mutableStateOf(false) }
    var currentCategoryToEdit by remember { mutableStateOf<Category?>(null) }
    var categoryNameInput by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
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
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    currentCategoryToEdit = null
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
            items(categories) { category ->
                CategoryCard(
                    category = category,
                    onEditClick = {
                        currentCategoryToEdit = category
                        categoryNameInput = TextFieldValue(category.nombre_categoria)
                        showCategoryDialog = true
                    },
                    onItemClick = { /* Ver detalles si se requiere */ }
                )
            }
        }
    }


    if (showCategoryDialog) {
        AlertDialog(
            onDismissRequest = { showCategoryDialog = false },
            title = {
                Text(if (currentCategoryToEdit == null) "Add New Category" else "Edit Category")
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
                            if (currentCategoryToEdit == null) {
                                viewModel.addCategory(categoryNameInput.text)
                            } else {
                                viewModel.editCategory(currentCategoryToEdit!!.id, categoryNameInput.text)
                            }
                        }
                        showCategoryDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CoffeeBrown, contentColor = TextWhite)
                ) {
                    Text(if (currentCategoryToEdit == null) "Add" else "Save")
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
    category: Category,
    onEditClick: () -> Unit,
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
                text = category.nombre_categoria,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = onEditClick) {
                    Text("Edit", color = TextWhite)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Go to details",
                    tint = TextWhite,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}