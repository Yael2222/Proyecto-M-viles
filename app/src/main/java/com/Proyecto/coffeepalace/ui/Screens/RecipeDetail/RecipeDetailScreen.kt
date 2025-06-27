package com.Proyecto.coffeepalace.ui.Screens.Search

/*import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.Proyecto.coffeepalace.ui.Screens.ProductDetail.AddCommentBottomSheet
import com.Proyecto.coffeepalace.ui.Screens.ProductDetail.ProductViewModel
import com.Proyecto.coffeepalace.ui.components.CommentCard
import com.Proyecto.coffeepalace.ui.components.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    navController: NavHostController,
    recipeId: Int,
    viewModel: SearchViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val recipe = viewModel.getRecipeById(recipeId)
    val expanded = remember { mutableStateOf(false) }
    val showBottomSheet = remember { mutableStateOf(false) }
    val productViewModel = remember { ProductViewModel() }

    if (recipe == null) {
        Text("Receta no encontrada")
        return
    }

    if (showBottomSheet.value) {
        ModalBottomSheet(onDismissRequest = { showBottomSheet.value = false }) {
            AddCommentBottomSheet(viewModel = productViewModel) {
                showBottomSheet.value = false
            }
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
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(8.dp))

            Text(recipe.title, style = MaterialTheme.typography.titleLarge)

            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(recipe.rating.toInt()) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107))
                }
                Text(" ${recipe.ratingCount}")
            }

            Spacer(Modifier.height(8.dp))
            Text("Ingredients:", fontWeight = FontWeight.Bold)
            recipe.ingredients.forEach { Text(it) }

            Spacer(Modifier.height(8.dp))
            Text("Instructions:", fontWeight = FontWeight.Bold)
            Text(
                text = if (expanded.value) recipe.instructions else recipe.instructions.take(100) + "...",
                modifier = Modifier.clickable { expanded.value = !expanded.value }
            )
            if (!expanded.value) {
                Text("More...", modifier = Modifier.clickable { expanded.value = true }, color = Color.Gray)
            }

            Spacer(Modifier.height(8.dp))
            Text("Comments", fontWeight = FontWeight.Bold)
            Text("Add a comment", color = Color(0xFF8A4F2E), modifier = Modifier.clickable {
                showBottomSheet.value = true
            })

            Spacer(Modifier.height(8.dp))
            LazyRow {
                items(recipe.comments) {
                    CommentCard(text = String, stars = Int)
                }
            }
        }
    }
}
*/