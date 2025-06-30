package com.Proyecto.coffeepalace.ui.Screens.Customer.RecipeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.ComentarioReceta
import com.Proyecto.coffeepalace.Data.Model.Ingredient
import com.Proyecto.coffeepalace.Data.Repository.ComentarioRecetaRepository
import com.Proyecto.coffeepalace.Data.Repository.IngredientRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val comentarioRepository: ComentarioRecetaRepository,
    private val ingredientRepository: IngredientRepository
) : ViewModel() {

    private val _comments = MutableStateFlow<List<ComentarioReceta>>(emptyList())
    val comments: StateFlow<List<ComentarioReceta>> = _comments.asStateFlow()

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients: StateFlow<List<Ingredient>> = _ingredients.asStateFlow()

    private val _newCommentText = MutableStateFlow("")
    val newCommentText: StateFlow<String> = _newCommentText.asStateFlow()

    private val _newCommentRating = MutableStateFlow(5)
    val newCommentRating: StateFlow<Int> = _newCommentRating.asStateFlow()

    val averageRating: StateFlow<Float> = comments.map { list ->
        if (list.isNotEmpty()) list.map { it.rating }.average().toFloat() else 0f
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0f)

    fun cargarComentarios(idReceta: Int) {
        viewModelScope.launch {
            try {
                comentarioRepository.syncComentarios(idReceta)
                comentarioRepository.getComentariosByReceta(idReceta).collect {
                    _comments.value = it
                }
            } catch (e: Exception) {
                println("Error loading comments: ${e.message}")
            }
        }
    }

    fun cargarIngredientes(idReceta: Int) {
        viewModelScope.launch {
            try {
                val ingredientes = ingredientRepository.getIngredientesByReceta(idReceta)
                _ingredients.value = ingredientes
            } catch (e: Exception) {
                println("Error loading ingredients: ${e.message}")
            }
        }
    }

    fun agregarComentario(idReceta: Int, idUsuario: Int) {
        if (_newCommentText.value.isBlank()) return

        viewModelScope.launch {
            try {
                val comentario = ComentarioReceta(
                    idReceta = idReceta,
                    idUsuario = idUsuario,
                    text = _newCommentText.value.trim(),
                    rating = _newCommentRating.value
                )
                comentarioRepository.insertComentario(comentario)
                _newCommentText.value = ""
                _newCommentRating.value = 5
            } catch (e: Exception) {
                println("Error adding comment: ${e.message}")
            }
        }
    }

    fun updateCommentText(text: String) {
        _newCommentText.value = text
    }

    fun updateCommentRating(rating: Int) {
        _newCommentRating.value = rating
    }
}







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