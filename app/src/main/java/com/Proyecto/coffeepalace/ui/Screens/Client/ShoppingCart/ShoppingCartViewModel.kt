package com.Proyecto.coffeepalace.ui.Screens.Client.ShoppingCart

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.CartItem
import com.Proyecto.coffeepalace.Data.Network.CreateOrderRequest
import com.Proyecto.coffeepalace.Data.Repository.AuthRepository
import com.Proyecto.coffeepalace.Data.Repository.OrderRepository
import com.Proyecto.coffeepalace.Data.Repository.ShoppingCartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Si usas un patrón de eventos, esta clase de eventos es útil
sealed class ShoppingCartEvent {
    object OrderCreatedSuccessfully : ShoppingCartEvent()
    data class Error(val message: String) : ShoppingCartEvent()
    // Puedes añadir más eventos si necesitas
}

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val authRepository: AuthRepository,
    private val orderRepository: OrderRepository // Asumo que se usa para el checkout
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _checkoutSuccess = MutableStateFlow(false)
    val checkoutSuccess: StateFlow<Boolean> = _checkoutSuccess.asStateFlow()

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice.asStateFlow()

    init {
        loadCartItems()
    }

    /**
     * Obtiene el ID de autenticación del usuario actual desde AuthRepository.
     * Es crucial que este ID sea el UUID (String) del usuario.
     */
    private fun getCurrentUserId(): String? {
        val currentAuthId = authRepository.getCurrentAuthId()
        Log.d("ShoppingCartViewModel", "Current Auth ID from AuthRepository: $currentAuthId")
        return currentAuthId
    }

    /**
     * Carga todos los ítems del carrito para el usuario actual.
     * Actualiza los estados de carga, error, ítems del carrito y precio total.
     */
    fun loadCartItems() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _checkoutSuccess.value = false // Resetear estado de checkout por si acaso

            val userId = getCurrentUserId()
            Log.d("ShoppingCartViewModel", "Attempting to load cart for userId: $userId")

            if (userId == null) {
                val errorMessage = "Debes iniciar sesión para ver tu carrito (ID de usuario no disponible)."
                _error.value = errorMessage
                _isLoading.value = false
                _cartItems.value = emptyList()
                _totalPrice.value = 0.0
                Log.e("ShoppingCartViewModel", errorMessage)
                return@launch
            }

            try {
                // Llama al repositorio para obtener los ítems agregados del carrito
                val items = shoppingCartRepository.getAggregatedCartItemsForUser(userId)
                _cartItems.value = items
                calculateTotalPrice(items)
                Log.d("ShoppingCartViewModel", "Cart items loaded successfully: ${items.size} items.")
            } catch (e: Exception) {
                val errorMessage = "Error al cargar el carrito: ${e.localizedMessage}"
                _error.value = errorMessage
                Log.e("ShoppingCartViewModel", errorMessage, e) // Log el error completo
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Aumenta la cantidad de una unidad de un producto específico en el carrito.
     * Llama a addProductToCart en el repositorio y refresca el carrito.
     */
    fun increaseQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            _error.value = null
            val userId = getCurrentUserId()
            if (userId == null) {
                _error.value = "Inicia sesión para modificar el carrito."
                Log.e("ShoppingCartViewModel", "Cannot increase quantity: User ID is null.")
                return@launch
            }

            try {
                Log.d("ShoppingCartViewModel", "Increasing quantity for product ID: ${cartItem.productId}, User ID: $userId")
                // Llama al repositorio para añadir una nueva entrada de carrito para este producto.
                val newEntry = shoppingCartRepository.addProductToCart(userId,
                    cartItem.productId.toString() // Asegúrate de que productId es String si el backend lo espera así
                )
                if (newEntry != null) { // Si la operación fue exitosa, newEntry no será null
                    Log.d("ShoppingCartViewModel", "Successfully added one unit for product ID: ${cartItem.productId}")
                    loadCartItems() // Refresca el carrito para que la UI refleje el cambio
                } else {
                    _error.value = "Error al añadir una unidad del producto (operación fallida)."
                    Log.e("ShoppingCartViewModel", "Failed to add one unit for product ID: ${cartItem.productId}")
                }
            } catch (e: Exception) {
                val errorMessage = "Error al aumentar cantidad para el producto ${cartItem.productId}: ${e.localizedMessage}"
                _error.value = errorMessage
                Log.e("ShoppingCartViewModel", errorMessage, e)
            }
        }
    }

    /**
     * Disminuye la cantidad de una unidad de un producto específico en el carrito.
     * Llama a removeOneUnitFromCart en el repositorio y refresca el carrito.
     */
    fun decreaseQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            _error.value = null
            val userId = getCurrentUserId()
            if (userId == null) {
                _error.value = "Inicia sesión para modificar el carrito."
                Log.e("ShoppingCartViewModel", "Cannot decrease quantity: User ID is null.")
                return@launch
            }

            if (cartItem.quantity > 0) { // Solo si la cantidad es mayor que 0
                try {
                    // Tomamos el primer cartEntryId disponible para eliminar una unidad.
                    val cartEntryIdToDelete = cartItem.cartEntryIds.firstOrNull()

                    if (cartEntryIdToDelete != null) {
                        Log.d("ShoppingCartViewModel", "Decreasing quantity for product ID: ${cartItem.productId}, Cart Entry ID to delete: $cartEntryIdToDelete")
                        val success = shoppingCartRepository.removeOneUnitFromCart(cartEntryIdToDelete)
                        if (success) {
                            Log.d("ShoppingCartViewModel", "Successfully removed one unit for product ID: ${cartItem.productId} (cart entry ID: $cartEntryIdToDelete)")
                            loadCartItems() // Refresca el carrito
                        } else {
                            _error.value = "Error al quitar una unidad del producto (operación fallida)."
                            Log.e("ShoppingCartViewModel", "Failed to remove one unit for product ID: ${cartItem.productId} (cart entry ID: $cartEntryIdToDelete)")
                        }
                    } else {
                        _error.value = "No hay entradas de carrito para este producto para disminuir."
                        Log.w("ShoppingCartViewModel", "No cart entries found to decrease quantity for product ID: ${cartItem.productId}")
                    }
                } catch (e: Exception) {
                    val errorMessage = "Error al disminuir cantidad para el producto ${cartItem.productId}: ${e.localizedMessage}"
                    _error.value = errorMessage
                    Log.e("ShoppingCartViewModel", errorMessage, e)
                }
            } else {
                Log.w("ShoppingCartViewModel", "Quantity is already 0 for product ID: ${cartItem.productId}. Cannot decrease further.")
                _error.value = "La cantidad ya es 0, no se puede disminuir más."
            }
        }
    }

    /**
     * Elimina todas las unidades de un producto específico del carrito.
     * Itera sobre todos los cartEntryIds y llama a removeOneUnitFromCart para cada uno.
     */
    fun removeAllUnitsOfProduct(cartItem: CartItem) {
        viewModelScope.launch {
            _error.value = null
            val userId = getCurrentUserId()
            if (userId == null) {
                _error.value = "Inicia sesión para modificar el carrito."
                Log.e("ShoppingCartViewModel", "Cannot remove all units: User ID is null.")
                return@launch
            }

            try {
                Log.d("ShoppingCartViewModel", "Removing all units for product ID: ${cartItem.productId}")
                var allSuccess = true
                if (cartItem.cartEntryIds.isNotEmpty()) {
                    for (cartEntryId in cartItem.cartEntryIds) {
                        Log.d("ShoppingCartViewModel", "Attempting to remove cart entry ID: $cartEntryId for product ID: ${cartItem.productId}")
                        val success = shoppingCartRepository.removeOneUnitFromCart(cartEntryId)
                        if (!success) {
                            allSuccess = false
                            _error.value = "Falló la eliminación de una entrada del carrito (ID: $cartEntryId)."
                            Log.e("ShoppingCartViewModel", "Failed to remove cart entry $cartEntryId for product ID: ${cartItem.productId}")
                            break // Si falla uno, podríamos detenernos o intentar eliminar el resto
                        }
                    }
                }

                if (allSuccess) {
                    Log.d("ShoppingCartViewModel", "Successfully removed all units for product ID: ${cartItem.productId}")
                    loadCartItems() // Refresca el carrito para que la UI se actualice
                } else {
                    _error.value = "Error al eliminar todas las unidades del producto."
                    Log.e("ShoppingCartViewModel", "Failed to remove all units for product ID: ${cartItem.productId} (some entries might remain)")
                }

            } catch (e: Exception) {
                val errorMessage = "Error al eliminar el producto ${cartItem.productId} del carrito: ${e.localizedMessage}"
                _error.value = errorMessage
                Log.e("ShoppingCartViewModel", errorMessage, e)
            }
        }
    }

    /**
     * Procede al proceso de checkout.
     * Crea un objeto CreateOrderRequest y lo envía al OrderRepository.
     * Actualiza los estados de carga, error y éxito.
     */
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun checkout() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _checkoutSuccess.value = false

            val userId = getCurrentUserId()
            if (userId == null) {
                _error.value = "Debes iniciar sesión para completar la compra."
                _isLoading.value = false
                Log.e("ShoppingCartViewModel", "Cannot checkout: User ID is null.")
                return@launch
            }

            if (_cartItems.value.isEmpty()) {
                _error.value = "El carrito está vacío, no se puede realizar la compra."
                _isLoading.value = false
                Log.w("ShoppingCartViewModel", "Attempted checkout with empty cart.")
                return@launch
            }

            try {
                Log.d("ShoppingCartViewModel", "Initiating checkout for user ID: $userId")

                // Mapear CartItem a CreateOrderRequest.Product
                val productsForOrder = _cartItems.value.map { cartItem ->
                    CreateOrderRequest.Product(
                        productId = cartItem.productId, // Asegúrate de que productId es Int aquí
                        quantity = cartItem.quantity
                    )
                }

                val request = CreateOrderRequest(
                    userId = userId, // auth_id (UUID)
                    totalAmount = _totalPrice.value,
                    products = productsForOrder
                )

                // Llama al orderRepository con el objeto de solicitud
                val response = orderRepository.createOrder(request) // Ahora devuelve CreateOrderResponse?

                if (response != null) {
                    _checkoutSuccess.value = true // Indica éxito
                    Log.d("ShoppingCartViewModel", "Checkout successful for user ID: $userId, Invoice ID: ${response.invoice.id}")
                    loadCartItems() // Refresca el carrito (debería vaciarse si el backend lo hizo)
                } else {
                    _error.value = "Error al completar la compra (respuesta nula)."
                    Log.e("ShoppingCartViewModel", "Checkout failed for user ID: $userId (Repository returned null response).")
                }
            } catch (e: Exception) {
                val errorMessage = "Error inesperado durante la compra: ${e.localizedMessage}"
                _error.value = errorMessage
                Log.e("ShoppingCartViewModel", errorMessage, e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Calcula el precio total sumando el precio de cada producto por su cantidad.
     */
    private fun calculateTotalPrice(items: List<CartItem>) {
        _totalPrice.value = items.sumOf { it.productPrice * it.quantity }
        Log.d("ShoppingCartViewModel", "Total price calculated: ${_totalPrice.value}")
    }

    /**
     * Limpia el mensaje de error.
     */
    fun clearError() {
        _error.value = null
        Log.d("ShoppingCartViewModel", "Error state cleared.")
    }

    /**
     * Limpia el estado de éxito del checkout.
     */
    fun clearCheckoutSuccess() {
        _checkoutSuccess.value = false
        Log.d("ShoppingCartViewModel", "Checkout success state cleared.")
    }
}

/**
 * Factory para crear instancias de ShoppingCartViewModel.
 * Necesario para inyectar las dependencias de los repositorios.
 */
class ShoppingCartViewModelFactory(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val authRepository: AuthRepository,
    private val orderRepository: OrderRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingCartViewModel(shoppingCartRepository, authRepository, orderRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}