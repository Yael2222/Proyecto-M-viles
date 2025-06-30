package com.Proyecto.coffeepalace.ui.Screens.Customer.Search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.Ingredient
import com.Proyecto.coffeepalace.Data.Model.Product
import com.Proyecto.coffeepalace.Data.Model.Recipe
import com.Proyecto.coffeepalace.Data.Model.SearchResult
import com.Proyecto.coffeepalace.Data.Repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults: StateFlow<List<SearchResult>> = _searchResults.asStateFlow()

    private val _recentSearches = MutableStateFlow<List<String>>(emptyList())
    val recentSearches: StateFlow<List<String>> = _recentSearches.asStateFlow()

    private val _selectedFilters = MutableStateFlow<Set<String>>(emptySet())
    val selectedFilters: StateFlow<Set<String>> = _selectedFilters.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients: StateFlow<List<Ingredient>> = _ingredients.asStateFlow()

    init {
        loadIngredients()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun performSearch() {
        val query = _searchQuery.value.trim()
        if (query.isEmpty()) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val results = mutableListOf<SearchResult>()

                // Buscar por nombre de producto/receta
                val products: List<Product> = searchRepository.searchProducts(query)
                val recipes: List<Recipe> = searchRepository.searchRecipes(query)

                results.addAll(products.map { product -> SearchResult.ProductResult(product) })
                results.addAll(recipes.map { recipe -> SearchResult.RecipeResult(recipe) })

                // Buscar por ingrediente
                val ingredientResults: List<SearchResult> = searchRepository.searchByIngredient(query)
                results.addAll(ingredientResults)

                _searchResults.value = results.distinctBy { result ->
                    when (result) {
                        is SearchResult.ProductResult -> "product_${result.product.id}"
                        is SearchResult.RecipeResult -> "recipe_${result.recipe.id}"
                    }
                }

                // Agregar a búsquedas recientes
                addToRecentSearches(query)
            } catch (e: Exception) {
                println("Error performing search: ${e.message}")
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeFilter(filter: String) {
        val currentFilters = _selectedFilters.value.toMutableSet()
        currentFilters.remove(filter)
        _selectedFilters.value = currentFilters
    }

    private fun addToRecentSearches(query: String) {
        val current = _recentSearches.value.toMutableList()
        current.remove(query)
        current.add(0, query)
        if (current.size > 10) {
            current.removeAt(current.size - 1)
        }
        _recentSearches.value = current
    }

    private fun loadIngredients() {
        viewModelScope.launch {
            try {
                _ingredients.value = searchRepository.getAllIngredients()
            } catch (e: Exception) {
                println("Error loading ingredients: ${e.message}")
                _ingredients.value = emptyList()
            }
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _searchResults.value = emptyList()
        _selectedFilters.value = emptySet()
    }

    fun searchFromRecent(query: String) {
        updateSearchQuery(query)
        performSearch()
    }

    fun searchIngredient(ingredient: String) {
        updateSearchQuery(ingredient)
        performSearch()
    }
}





/*import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.Ingredient
import com.Proyecto.coffeepalace.Data.Model.Product
import com.Proyecto.coffeepalace.Data.Model.Recipe
import com.Proyecto.coffeepalace.Data.Model.SearchResult
import com.Proyecto.coffeepalace.Data.Repository.SearchRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults: StateFlow<List<SearchResult>> = _searchResults.asStateFlow()

    private val _recentSearches = MutableStateFlow<List<String>>(emptyList())
    val recentSearches: StateFlow<List<String>> = _recentSearches.asStateFlow()

    private val _selectedFilters = MutableStateFlow<Set<String>>(emptySet())
    val selectedFilters: StateFlow<Set<String>> = _selectedFilters.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients: StateFlow<List<Ingredient>> = _ingredients.asStateFlow()

    init {
        loadIngredients()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun performSearch() {
        val query = _searchQuery.value.trim()
        if (query.isEmpty()) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val results = mutableListOf<SearchResult>()

                // Buscar por nombre de producto/receta
                val products: List<Product> = searchRepository.searchProducts(query)
                val recipes: List<Recipe> = searchRepository.searchRecipes(query)

                results.addAll(products.map { product -> SearchResult.ProductResult(product) })
                results.addAll(recipes.map { recipe -> SearchResult.RecipeResult(recipe) })

                // Buscar por ingrediente
                val ingredientResults: List<SearchResult> = searchRepository.searchByIngredient(query)
                results.addAll(ingredientResults)

                _searchResults.value = results.distinctBy { result ->
                    when (result) {
                        is SearchResult.ProductResult -> "product_${result.product.id}"
                        is SearchResult.RecipeResult -> "recipe_${result.recipe.id}"
                    }
                }

                // Agregar a búsquedas recientes
                addToRecentSearches(query)
            } catch (e: Exception) {
                println("Error performing search: ${e.message}")
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeFilter(filter: String) {
        val currentFilters = _selectedFilters.value.toMutableSet()
        currentFilters.remove(filter)
        _selectedFilters.value = currentFilters
    }

    private fun addToRecentSearches(query: String) {
        val current = _recentSearches.value.toMutableList()
        current.remove(query)
        current.add(0, query)
        if (current.size > 10) {
            current.removeAt(current.size - 1)
        }
        _recentSearches.value = current
    }

    private fun loadIngredients() {
        viewModelScope.launch {
            try {
                _ingredients.value = searchRepository.getAllIngredients()
            } catch (e: Exception) {
                println("Error loading ingredients: ${e.message}")
                _ingredients.value = emptyList()
            }
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _searchResults.value = emptyList()
        _selectedFilters.value = emptySet()
    }

    fun searchFromRecent(query: String) {
        updateSearchQuery(query)
        performSearch()
    }

    fun searchIngredient(ingredient: String) {
        updateSearchQuery(ingredient)
        performSearch()
    }
}
 */





/*import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.Busqueda.BusquedaRecienteDao
import com.Proyecto.coffeepalace.Data.Model.BusquedaReciente
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.Proyecto.coffeepalace.Data.Model.producto
import com.Proyecto.coffeepalace.Data.Model.receta


class SearchViewModel(
    private val dao: BusquedaRecienteDao
) : ViewModel() {

    var resultadosProducto = mutableStateListOf<producto>()
    var resultadosReceta = mutableStateListOf<receta>()
    var recientes = mutableStateListOf<BusquedaReciente>()

    var query by mutableStateOf("")

    init {
        viewModelScope.launch {
            recientes.addAll(dao.getRecientes())
        }
    }

    fun buscar() {
        viewModelScope.launch {
            if (query.isBlank()) return@launch

            dao.insertar(BusquedaReciente(texto = query))
            recientes.clear()
            recientes.addAll(dao.getRecientes())

            val (productos, recetas) = buscarEnSupabase(query)
            resultadosProducto.clear()
            resultadosProducto.addAll(productos)
            resultadosReceta.clear()
            resultadosReceta.addAll(recetas)
        }
    }
}
*/





/*import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.Busqueda.DaoProductoImpl
import com.Proyecto.coffeepalace.Data.Daos.Busqueda.DaoRecetaImpl
import com.Proyecto.coffeepalace.Data.Model.producto
import com.Proyecto.coffeepalace.Data.Model.receta
import com.Proyecto.coffeepalace.Data.Repository.SearchRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = SearchRepository(DaoProductoImpl(), DaoRecetaImpl())

    val query = mutableStateOf("")
    val productos = mutableStateOf<List<producto>>(emptyList())
    val recetas = mutableStateOf<List<receta>>(emptyList())

    val recentSearches = mutableStateListOf<String>()

    private var searchJob: Job? = null

    fun onQueryChanged(newQuery: String) {
        query.value = newQuery
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            buscar(query.value)
        }
    }

    fun buscar(texto: String) {
        viewModelScope.launch {
            val (productosRes, recetasRes) = repository.buscarTodo(texto.trim())
            productos.value = productosRes
            recetas.value = recetasRes

            if (texto.isNotBlank() && !recentSearches.contains(texto)) {
                recentSearches.add(0, texto)
            }
        }
    }

    fun removeRecent(index: Int) {
        if (index in recentSearches.indices) {
            recentSearches.removeAt(index)
        }
    }
}
 */


/*
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
*/