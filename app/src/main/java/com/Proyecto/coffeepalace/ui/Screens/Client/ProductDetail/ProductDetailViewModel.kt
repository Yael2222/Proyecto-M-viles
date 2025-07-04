package com.Proyecto.coffeepalace.ui.Screens.Client.ProductDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.producto
import com.Proyecto.coffeepalace.Data.Model.comentario_producto
import com.Proyecto.coffeepalace.Data.Repository.AuthRepository
import com.Proyecto.coffeepalace.Data.Repository.ComentariosRepository
import com.Proyecto.coffeepalace.Data.Repository.ProductRepository
import com.Proyecto.coffeepalace.Data.Repository.Result
import com.Proyecto.coffeepalace.Data.Repository.ShoppingCartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

class ProductDetailViewModel(
    private val productosRepository: ProductRepository,
    private val comentariosRepository: ComentariosRepository,
    private val authRepository: AuthRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId: Int = checkNotNull(savedStateHandle["productId"])

    private val _product = MutableStateFlow<producto?>(null)
    val product: StateFlow<producto?> = _product.asStateFlow()

    private val _comments = MutableStateFlow<List<comentario_producto>>(emptyList())
    val comments: StateFlow<List<comentario_producto>> = _comments.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null) // Usado para mensajes de Snackbar (éxito/error)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _showLoginRequiredDialog = MutableStateFlow(false)
    val showLoginRequiredDialog: StateFlow<Boolean> = _showLoginRequiredDialog.asStateFlow()


    init {
        loadProductDetails()
        loadComments()
    }

    internal fun loadProductDetails() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            when (val result = productosRepository.getProductoById(productId)) {
                is Result.Success -> {
                    _product.value = result.data
                }
                is Result.Error -> {
                    _error.value = result.exception.message
                    Log.e("ProductDetailVM", "Error loading product details: ${result.exception.message}", result.exception)
                }
                is Result.Loading -> {
                    Log.d("ProductDetailVM", "Product details are loading...")
                }
            }
            _isLoading.value = false
        }
    }

    fun showLoginDialog() {
        _showLoginRequiredDialog.value = true
    }

    fun loadComments() {
        viewModelScope.launch {
            _error.value = null

            when (val result = comentariosRepository.getComentariosByProductoId(productId)) {
                is Result.Success -> {
                    _comments.value = result.data
                }
                is Result.Error -> {
                    _error.value = result.exception.message
                    Log.e("ProductDetailVM", "Error loading comments: ${result.exception.message}", result.exception)
                }
                is Result.Loading -> {
                    Log.d("ProductDetailVM", "Comments are currently loading...")
                }
            }
        }
    }

    fun addComment(text: String, rating: Int) {
        if (!authRepository.isLoggedIn()) {
            _showLoginRequiredDialog.value = true
            return
        }

        viewModelScope.launch {
            _error.value = null
            if (text.isBlank()) {
                _error.value = "El comentario no puede estar vacío."
                return@launch
            }
            if (rating !in 1..5) {
                _error.value = "La calificación debe ser entre 1 y 5 estrellas."
                return@launch
            }

            val userId = authRepository.getCurrentAuthId() // Asume que devuelve String?
            if (userId == null) {
                _error.value = "Error: No se pudo obtener el ID del usuario. Por favor, inicia sesión de nuevo."
                _showLoginRequiredDialog.value = true
                return@launch
            }

            when (val result = comentariosRepository.addComentario(userId, productId, text, rating)) {
                is Result.Success -> {
                    Log.d("ProductDetailVM", "Comentario añadido con éxito.")
                    _error.value = "Comentario añadido con éxito."
                    loadComments()
                }
                is Result.Error -> {
                    _error.value = "Error al añadir comentario: ${result.exception.message}"
                    Log.e("ProductDetailVM", "Error adding comment: ${result.exception.message}", result.exception)
                }
                is Result.Loading -> {
                    Log.d("ProductDetailVM", "Adding comment is in progress...")
                }
            }
        }
    }

    fun addProductToCart() {
        viewModelScope.launch {
            _error.value = null

            val userId = authRepository.getCurrentAuthId() // Asume que devuelve String?
            if (userId == null) {
                _showLoginRequiredDialog.value = true
                return@launch
            }

            val currentProduct = _product.value
            if (currentProduct?.id == null) {
                _error.value = "No se pudo añadir el producto al carrito. ID del producto no disponible."
                return@launch
            }

            try {
                // MODIFICADO: id_producto debe ser String
                val added = shoppingCartRepository.addProductToCart(userId, currentProduct.id.toString())
                if (added != null) { // shoppingCartRepository.addProductToCart debería devolver el 'id' del carrito añadido o similar
                    _error.value = "Producto ${currentProduct.nombre} añadido al carrito."
                } else {
                    _error.value = "Fallo al añadir el producto ${currentProduct.nombre} al carrito."
                }
            } catch (e: Exception) {
                _error.value = "Error al añadir el producto al carrito: ${e.localizedMessage}"
                Log.e("ProductDetailVM", "Error adding product to cart: ${e.localizedMessage}", e)
            }
        }
    }

    fun dismissLoginRequiredDialog() {
        _showLoginRequiredDialog.value = false
    }

    fun clearError() {
        _error.value = null
    }
}