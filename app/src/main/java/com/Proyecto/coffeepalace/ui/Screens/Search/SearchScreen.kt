package com.Proyecto.coffeepalace.ui.Screens.Search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import com.Proyecto.coffeepalace.ui.Screens.Search.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = viewModel()
) {
    val query by viewModel.query
    val results = viewModel.searchResults
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Search", style = MaterialTheme.typography.titleLarge)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { viewModel.onQueryChanged(it) },
                    placeholder = { Text("Buscar platos, ingredientes y productos...") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search Icon")
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF8A4F2E),
                        unfocusedBorderColor = Color(0xFFD7CCC8),
                        cursorColor = Color(0xFF8A4F2E),
                        focusedLeadingIconColor = Color(0xFF8A4F2E),
                        unfocusedLeadingIconColor = Color(0xFF8A4F2E),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.search()
                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .focusRequester(focusRequester)
                )

                Spacer(Modifier.width(8.dp))

                Button(
                    onClick = { viewModel.search()
                        keyboardController?.hide()
                              },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8A4F2E),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("Buscar")
                }
            }


            Spacer(Modifier.height(8.dp))

            if (viewModel.suggestions.isNotEmpty()) {
                Text("Búsquedas sugeridas:")
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    viewModel.suggestions.forEach {
                        AssistChip(
                            onClick = {
                                viewModel.query.value = it
                                viewModel.search()
                            },
                            label = { Text(it) }
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            viewModel.recentSearches.forEachIndexed { index, search ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Search, contentDescription = null)
                    Text(
                        search,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                viewModel.query.value = search
                                viewModel.search()
                            }
                            .padding(start = 8.dp)
                    )
                    IconButton(onClick = { viewModel.recentSearches.removeAt(index) }) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            if (results.isEmpty() && query.isNotBlank()) {
                Text("No se encontraron resultados")
            } else {
                LazyColumn {
                    items(results) { recipe ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    navController.navigate("recipe_detail/${recipe.id}")
                                }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    recipe.title,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

