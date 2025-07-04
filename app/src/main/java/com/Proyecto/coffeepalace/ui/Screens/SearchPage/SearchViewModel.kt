package com.Proyecto.coffeepalace.ui.Screens.Search

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.categoria
import com.Proyecto.coffeepalace.Data.Model.ingrediente
import com.Proyecto.coffeepalace.Data.Model.producto
import com.Proyecto.coffeepalace.Data.Model.receta
import com.Proyecto.coffeepalace.Data.Repository.CategoryRepository
import com.Proyecto.coffeepalace.Data.Repository.IngredienteRepository
import com.Proyecto.coffeepalace.Data.Repository.ProductRepository
import com.Proyecto.coffeepalace.Data.Repository.RecetaRepository
import com.Proyecto.coffeepalace.Data.Repository.SearchRepository
import com.Proyecto.coffeepalace.Data.Repository.Result // Importa tu sealed class Result
import com.Proyecto.coffeepalace.di.AppContainer // Para la inyección de dependencias en el Factory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchResults(
    val products: List<producto> = emptyList(),
    val recipes: List<receta> = emptyList()
)

class SearchViewModel(
    private val productRepository: ProductRepository,
    private val recetaRepository: RecetaRepository,
    private val ingredienteRepository: IngredienteRepository,
    private val categoryRepository: CategoryRepository,
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow(TextFieldValue(""))
    val searchQuery: StateFlow<TextFieldValue> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow(SearchResults())
    val searchResults: StateFlow<SearchResults> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    private val _availableIngredients = MutableStateFlow<List<ingrediente>>(emptyList())
    val availableIngredients: StateFlow<List<ingrediente>> = _availableIngredients.asStateFlow()

    private val _selectedIngredients = MutableStateFlow<Set<ingrediente>>(emptySet())
    val selectedIngredients: StateFlow<Set<ingrediente>> = _selectedIngredients.asStateFlow()

    private val _categories = MutableStateFlow<List<categoria>>(emptyList())
    val categories: StateFlow<List<categoria>> = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow<categoria?>(null)
    val selectedCategory: StateFlow<categoria?> = _selectedCategory.asStateFlow()

    init {
        loadFilterOptions() // Carga los ingredientes y categorías al iniciar el ViewModel
    }

    private fun loadFilterOptions() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = ingredienteRepository.getAllIngredientes()) {
                    is Result.Success<*> -> _availableIngredients.value = result.data as List<ingrediente>
                    is Result.Error -> _snackbarMessage.value = "Error al cargar ingredientes: ${result.exception.message}"
                    else -> {}
                }
                when (val result = categoryRepository.getAllCategories()) {
                    is Result.Success<*> -> _categories.value = result.data as List<categoria>
                    is Result.Error -> _snackbarMessage.value = "Error al cargar categorías: ${result.exception.message}"
                    else -> {}
                }
            } catch (e: Exception) {
                _snackbarMessage.value = "Error al cargar opciones de filtro: ${e.message}"
                Log.e("SearchViewModel", "Error loading filter options: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearResults() {
        _searchResults.value = SearchResults(emptyList(), emptyList())
        _isLoading.value = false // Asegurarse de que no esté en estado de carga
    }

    fun onSearchQueryChanged(newQuery: TextFieldValue) {
        _searchQuery.value = newQuery
    }

    // Estas funciones ya no se usan directamente en la UI simplificada, pero se mantienen
    // si hay lógica interna que las use o si decides reintroducir los filtros.
    fun toggleIngredientSelection(ingredient: ingrediente) {
        _selectedIngredients.update { current ->
            if (current.contains(ingredient)) {
                current - ingredient
            } else {
                current + ingredient
            }
        }
    }

    fun onCategorySelected(category: categoria?) {
        _selectedCategory.value = category
    }

    fun performSearch() {
        viewModelScope.launch {
            _isLoading.value = true
            _searchResults.value = SearchResults()
            _snackbarMessage.value = null

            val queryText = searchQuery.value.text.trim() // Obtener el texto actual de la barra de búsqueda

            // Si la consulta está vacía, limpia los resultados y sal.
            if (queryText.isBlank()) {
                clearResults()
                return@launch
            }

            // --- Lógica de búsqueda universal ---
            // Aquí es donde construimos los parámetros para el searchRepository.searchItems
            // para que el backend pueda buscar en todos los campos relevantes.

            // Para buscar por categoría, intentamos encontrar una categoría cuyo nombre coincida con el queryText
            val categoryToFilter = categories.value.find {
                it.nombre.equals(queryText, ignoreCase = true)
            }
            val categoryIdToPass = categoryToFilter?.id

            // Para buscar por ingredientes, intentamos encontrar ingredientes cuyos nombres coincidan con el queryText
            val ingredientsToFilter = availableIngredients.value.filter {
                it.nombre.contains(queryText, ignoreCase = true)
            }
            val ingredientIdsToPass = if (ingredientsToFilter.isNotEmpty()) {
                ingredientsToFilter.mapNotNull { it.id }
            } else {
                null
            }

            // Realiza la llamada al SearchRepository.
            // Pasamos el queryText para la búsqueda por nombre.
            // Pasamos categoryIdToPass si encontramos una categoría que coincida.
            // Pasamos ingredientIdsToPass si encontramos ingredientes que coincidan.
            val result = searchRepository.searchItems(queryText, categoryIdToPass, ingredientIdsToPass)

            when (result) {
                is Result.Success -> {
                    _searchResults.value = SearchResults(
                        products = result.data.products,
                        recipes = result.data.recipes
                    )
                    if (result.data.products.isEmpty() && result.data.recipes.isEmpty()) {
                        _snackbarMessage.value = "No se encontraron resultados."
                    }
                }
                is Result.Error -> {
                    _snackbarMessage.value = "Error en la búsqueda: ${result.exception.message ?: "Desconocido"}"
                    Log.e("SearchViewModel", "Search failed: ${result.exception.message}", result.exception)
                }
                Result.Loading -> {
                    // Manejar estado de carga
                }
            }
            _isLoading.value = false
        }
    }

    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }
}

class SearchViewModelFactory(
    private val productRepository: ProductRepository,
    private val recetaRepository: RecetaRepository,
    private val ingredienteRepository: IngredienteRepository,
    private val categoryRepository: CategoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(
                productRepository = productRepository,
                recetaRepository = recetaRepository,
                ingredienteRepository = ingredienteRepository,
                categoryRepository = categoryRepository,
                searchRepository = AppContainer.searchRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}