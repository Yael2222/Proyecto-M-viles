package com.Proyecto.coffeepalace.ui.Screens.Search

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.Proyecto.coffeepalace.Data.AppDatabase
import com.Proyecto.coffeepalace.Data.Repository.ComentarioRecetaRepository
import com.Proyecto.coffeepalace.Data.Repository.IngredientRepository
import com.Proyecto.coffeepalace.ui.Screens.Customer.RecipeDetail.RecipeDetailViewModel
import com.Proyecto.coffeepalace.ui.Screens.Customer.Search.SearchViewModel
import com.Proyecto.coffeepalace.ui.components.AddCommentBottomSheetRecipe
import com.Proyecto.coffeepalace.ui.components.BottomBar
import io.ktor.websocket.Frame.Text
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil3.compose.AsyncImage
import com.Proyecto.coffeepalace.ui.components.CommentCard

@Composable
fun RecipeDetailScreen(
    navController: NavHostController,
    recipeId: Int,
    idUsuario: Int,
    appContext: Context,
    searchViewModel: SearchViewModel = viewModel()
) {
    val recipe = searchViewModel.getRecipeById(recipeId)
    if (recipe == null) {
        Text("Receta no encontrada")
        return
    }

    val dao = remember { AppDatabase.getDatabase(appContext).comentarioRecetaDao() }
    val comentarioRepository = remember { ComentarioRecetaRepository(dao) }
    val ingredientRepository = remember { IngredientRepository() }

    val viewModel = remember {
        RecipeDetailViewModel(comentarioRepository, ingredientRepository)
    }

    val comments by viewModel.comments.collectAsState()
    val ingredients by viewModel.ingredients.collectAsState()
    val averageRating by viewModel.averageRating.collectAsState()

    val expanded = remember { mutableStateOf(false) }
    val showBottomSheet = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.cargarComentarios(recipeId)
        viewModel.cargarIngredientes(recipeId)
    }

    if (showBottomSheet.value) {
        ModalBottomSheet(onDismissRequest = { showBottomSheet.value = false }) {
            AddCommentBottomSheetRecipe(
                viewModel = viewModel,
                onDismiss = { showBottomSheet.value = false },
                onSubmit = {
                    viewModel.agregarComentario(recipeId, idUsuario)
                    showBottomSheet.value = false
                }
            )
        }
    }

    Scaffold(
        bottomBar = {
            BottomBar(
                currentRoute = "search",
                onHomeClick = { navController.navigate("home") },
                onCategoryClick = { navController.navigate("category") },
                onSearchClick = { navController.navigate("search") },
                onProfileClick = { navController.navigate("profile") },
                onCartClick = { navController.navigate("cart") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }

            AsyncImage(
                model = recipe.imagen,
                contentDescription = recipe.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(8.dp))
            Text(recipe.nombre, style = MaterialTheme.typography.titleLarge)

            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(averageRating.toInt()) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107))
                }
                Text(" ${"%.1f".format(averageRating)}")
            }

            Spacer(Modifier.height(8.dp))
            Text("Ingredientes:", fontWeight = FontWeight.Bold)
            ingredients.forEach {
                Text("- ${it.nombre}")
            }

            Spacer(Modifier.height(8.dp))
            Text("Instrucciones:", fontWeight = FontWeight.Bold)
            Text(
                text = if (expanded.value) recipe.instrucciones else recipe.instrucciones.take(100) + "...",
                modifier = Modifier.clickable { expanded.value = !expanded.value }
            )

            Spacer(Modifier.height(8.dp))
            Text("Comentarios", fontWeight = FontWeight.Bold)
            Text(
                "Agregar comentario",
                color = Color(0xFF8A4F2E),
                modifier = Modifier.clickable { showBottomSheet.value = true }
            )

            Spacer(Modifier.height(8.dp))
            LazyRow {
                items(comments) { comment ->
                    CommentCard(text = comment.text, stars = comment.rating)
                }
            }
        }
    }
}
