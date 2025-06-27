package com.Proyecto.coffeepalace.ui.Screens.RecipeDetail

/*import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Database.Dao.CommentDao
import com.Proyecto.coffeepalace.Data.Database.Dao.RecipeDao
import com.Proyecto.coffeepalace.Data.Database.Entities.CommentEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

class RecipeDetailViewModel(
    private val recipeDao: RecipeDao,
    private val commentDao: CommentDao,
    private val recipeId: Int
) : ViewModel() {

    var state by mutableStateOf(RecipeDetailState())
        private set

    init {
        loadRecipe()
        loadComments()
    }

    private fun loadRecipe() {
        viewModelScope.launch {
            val recipes = recipeDao.getAllRecipes().first()
            val recipe = recipes.find { it.id == recipeId }
            state = state.copy(recipe = recipe)
        }
    }


    private fun loadComments() {
        viewModelScope.launch {
            val comments = commentDao.getCommentsForRecipe(recipeId).first()
            state = state.copy(comments = comments)
        }
    }

    fun onCommentChange(newComment: String) {
        state = state.copy(newComment = newComment)
    }

    fun postComment(userEmail: String) {
        viewModelScope.launch {
            if (state.newComment.isBlank()) return@launch

            val comment = CommentEntity(
                recipeId = recipeId,
                userEmail = userEmail,
                content = state.newComment
            )
            commentDao.insertComment(comment)
            state = state.copy(newComment = "")
            loadComments()
        }
    }
}*/

/*import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.Proyecto.coffeepalace.Data.Model.Recipe

/*class SearchViewModel : ViewModel() {

    // Lista de recetas simuladas (puedes reemplazarla luego por datos reales)
    private val recipes = listOf(
        Recipe(
            id = 1,
            title = "Deviled Eggs",
            imageUrl = "https://www.simplyrecipes.com/thmb/.../deviled-eggs.jpg",
            ingredients = listOf("6 eggs", "1/4 cup mayonnaise", "1 tsp mustard", "Salt", "Pepper"),
            instructions = "1. Hard boil the eggs\n2. Peel and cut in half\n3. Mix yolks with ingredients\n4. Fill whites with the mixture",
            rating = 4.5f,
            ratingCount = 123
        )
    )

    // Comentarios por receta (clave: ID receta)
    private val commentsMap = mutableMapOf<Int, MutableList<String>>().apply {
        put(1, mutableListOf("Affogato - Muy buenos estos huevos.", "Affogato - Me encantaron."))
    }

    // Estado observable para comentarios
    var currentComments = mutableStateListOf<String>()
        private set

    // Obtener receta por ID
    fun getRecipeById(id: Int): Recipe? {
        val recipe = recipes.find { it.id == id }
        if (recipe != null) {
            // Cargar comentarios correspondientes
            val comments = commentsMap[recipe.id] ?: mutableListOf()
            currentComments.clear()
            currentComments.addAll(comments)
        }
        return recipe
    }

    // Agregar nuevo comentario
    fun addComment(recipeId: Int, comment: String) {
        val comments = commentsMap.getOrPut(recipeId) { mutableListOf() }
        comments.add(comment)
        currentComments.add(comment)
    }
}*/