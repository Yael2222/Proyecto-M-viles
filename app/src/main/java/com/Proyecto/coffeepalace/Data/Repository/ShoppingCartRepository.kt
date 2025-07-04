package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Model.carrito
import com.Proyecto.coffeepalace.Data.Model.CartItem
import com.Proyecto.coffeepalace.Data.Network.ApiService
import java.lang.Exception
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShoppingCartRepository(
    private val apiService: ApiService,
    private val productRepository: ProductRepository // Still needed to fetch product details
) {

    suspend fun addProductToCart(userId: String, productId: String): carrito? { // userId y productId como String
        return try {
            val newCartEntry = carrito(id_producto = productId, id_usuario = userId)
            apiService.addCartItem(newCartEntry)
        } catch (e: Exception) {
            null // O manejar el error de forma más robusta
        }
    }

    suspend fun getAggregatedCartItemsForUser(userId: String): List<CartItem> {
        return withContext(Dispatchers.IO) {
            try {
                val rawCartItems = apiService.getRawCartItemsForUser(userId)
                val aggregatedCartItems = mutableListOf<CartItem>()

                val groupedByProduct = rawCartItems.groupBy { it.id_producto }

                for ((productIdString, entries) in groupedByProduct) {
                    val productIdLong = productIdString.toLongOrNull() ?: continue

                    // Asegúrate de que getProductoById en ProductRepository puede manejar Long si es necesario.
                    // Si tu tabla de productos tiene IDs que pueden ser Long, asegúrate de que ProductRepository
                    // no los convierta a Int si eso causa un desbordamiento o error.
                    when (val result = productRepository.getProductoById(productIdLong.toInt())) { // Considera productIdLong si getProductoById acepta Long
                        is Result.Success -> {
                            val productDetails = result.data
                            val cartEntryIds = entries.mapNotNull { it.id }.toMutableList()
                            val quantity = entries.size

                            aggregatedCartItems.add(
                                CartItem(
                                    productId = productIdLong,
                                    productName = productDetails.nombre,
                                    productPrice = productDetails.precio,
                                    productImageUrl = productDetails.imagen,
                                    quantity = quantity,
                                    cartEntryIds = cartEntryIds
                                )
                            )
                        }
                        is Result.Error -> {
                            // Esta parte es CRÍTICA si quieres ver *todos* los ítems, incluso si falla un producto.
                            // Tu log anterior mostró que /api/productos/1 respondió 200 OK, lo cual es genial.
                            // Si SIGUE fallando (por ejemplo, si el producto ID=1 no existe), esta parte es importante.
                            println("Error al obtener detalles del producto con ID $productIdLong: ${result.exception.localizedMessage}. Agregando ítem parcial.")
                            aggregatedCartItems.add(
                                CartItem(
                                    productId = productIdLong,
                                    productName = "Producto desconocido (ID: $productIdLong)",
                                    productPrice = 0.0, // Precio de fallback
                                    productImageUrl = "", // Imagen de fallback
                                    quantity = entries.size,
                                    cartEntryIds = entries.mapNotNull { it.id }.toMutableList()
                                )
                            )
                        }
                        is Result.Loading -> {
                            // Este estado no debería ser final en este contexto
                        }
                    }
                }
                aggregatedCartItems
            } catch (e: Exception) {
                println("Error al obtener y agregar items del carrito: ${e.localizedMessage}")
                e.printStackTrace()
                emptyList()
            }
        }
    }


    suspend fun removeOneUnitFromCart(cartEntryId: Long): Boolean {
        return try {
            apiService.deleteCartEntry(cartEntryId) // Esto ahora devolverá Unit correctamente
            true // Si llega aquí, es exitoso
        } catch (e: Exception) {
            println("Error al eliminar una unidad del carrito: ${e.localizedMessage}")
            e.printStackTrace()
            false
        }
    }

    // Clears all cart entries for a user.
    suspend fun clearUserCart(userId: String): Boolean {
        return try {
            apiService.clearUserCart(userId)
            true
        } catch (e: Exception) {
            println("Error al vaciar el carrito: ${e.localizedMessage}")
            e.printStackTrace()
            false
        }
    }
}