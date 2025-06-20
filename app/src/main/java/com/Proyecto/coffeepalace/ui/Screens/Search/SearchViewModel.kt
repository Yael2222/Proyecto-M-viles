package com.Proyecto.coffeepalace.ui.Screens.Search

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.Recipe
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    val query = mutableStateOf("")

    private val _searchResults = mutableStateOf(listOf<Recipe>())
    val searchResults: List<Recipe> get() = _searchResults.value

    val suggestions = listOf("Eggs", "Pizza", "Soap")

    val recentSearches = mutableStateListOf<String>()

    private var searchJob: Job? = null // para debounce

    private val allRecipes = listOf(
        Recipe(
            id = 1,
            title = "Deviled Eggs",
            imageUrl = "https://static01.nyt.com/images/2021/10/15/dining/aw-classic-deviled-eggs/aw-classic-deviled-eggs-mediumSquareAt3X.jpg",
            rating = 4.5f,
            ratingCount = 120,
            ingredients = listOf("Eggs", "Mayonnaise"),
            instructions = "Cook the eggs..."
        ),
        Recipe(
            id = 2,
            title = "Pizza de masa madre",
            imageUrl = "https://assets.elgourmet.com/wp-content/uploads/2023/03/cover_vhlf5orm7s_pizzamasa.jpg",
            rating = 4.8f,
            ratingCount = 80,
            ingredients = listOf("Pasta", "Bacon", "Eggs"),
            instructions = "Boil pasta..."
        )
    )

    fun onQueryChanged(newQuery: String) {
        query.value = newQuery
        searchJob?.cancel() // cancela búsquedas anteriores si aún no terminan

        searchJob = viewModelScope.launch {
            delay(300) // debounce espera a que el usuario deje de escribir
            search()   // ejecuta la búsqueda después del delay
        }
    }

    fun search() {
        val currentQuery = query.value.trim().lowercase()
        if (currentQuery.isNotBlank()) {
            val filtered = allRecipes.filter { recipe ->
                recipe.title.lowercase().contains(currentQuery) ||
                        recipe.ingredients.any { it.lowercase().contains(currentQuery) }
            }.sortedBy { recipe -> // ordenar para que aparezca más relevante primero
                val index = recipe.title.lowercase().indexOf(currentQuery)
                if (index >= 0) index else Int.MAX_VALUE
            }

            _searchResults.value = filtered

            if (currentQuery !in recentSearches) {
                recentSearches.add(0, currentQuery)
            }
        } else {
            _searchResults.value = emptyList()
        }
    }

    fun removeRecentSearch(index: Int) {
        if (index in recentSearches.indices) {
            recentSearches.removeAt(index)
        }
    }

    fun getRecipeById(id: Int): Recipe? {
        return allRecipes.find { it.id == id }
    }
}
