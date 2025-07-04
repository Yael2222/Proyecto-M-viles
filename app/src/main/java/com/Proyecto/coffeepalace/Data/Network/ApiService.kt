package com.Proyecto.coffeepalace.Data.Network

import com.Proyecto.coffeepalace.Data.Model.AddComentarioRequest
import com.Proyecto.coffeepalace.Data.Model.categoria
import com.Proyecto.coffeepalace.Data.Model.ingrediente
import com.Proyecto.coffeepalace.Data.Model.producto
import com.Proyecto.coffeepalace.Data.Model.usuario
import com.Proyecto.coffeepalace.Data.Model.receta
import com.Proyecto.coffeepalace.Data.Model.receta_ingrediente
import com.Proyecto.coffeepalace.Data.Model.OrdenWithDetails
import com.Proyecto.coffeepalace.Data.Model.orden_vendedor
import com.Proyecto.coffeepalace.Data.Model.AuthResponse
import com.Proyecto.coffeepalace.Data.Model.CreateOrderResponse
import com.Proyecto.coffeepalace.Data.Model.RecetaWithIngredientes
import com.Proyecto.coffeepalace.Data.Model.carrito
import com.Proyecto.coffeepalace.Data.Model.comentario_producto
import retrofit2.http.PATCH
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.Query
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class UpdateOrderStatusRequest(val estado: String)


data class AuthRequest(val email: String, val password: String)
data class SignUpRequest(
    val email: String,
    val password: String,
    val name: String
)
data class ResetPasswordRequest(val email: String)
data class GoogleSignInTokenRequest(
    val idToken: String
)
data class CreateOrderRequest(
    @SerializedName("userId") // Asegúrate de que el nombre del campo coincida con tu backend
    val userId: String, // Asumo que el userId es un UUID (String)
    @SerializedName("totalAmount")
    val totalAmount: Double,
    @SerializedName("products")
    val products: List<Product> // Aquí se hace referencia a la clase anidada Product
) {
    // Define la clase Product como una clase anidada dentro de CreateOrderRequest
    data class Product(
        @SerializedName("productId") // Asegúrate de que el nombre del campo coincida con tu backend
        val productId: Long, // O String, si tus IDs de producto son Strings
        @SerializedName("quantity")
        val quantity: Int
    )
}

data class OrderProductDetail(
    val productId: Long, // El ID del producto de la tabla 'producto'
    val quantity: Int    // La cantidad de ese producto
)

interface ApiService {

    @GET("search") // Asegúrate de que esta ruta coincida con tu backend
    suspend fun searchItems(
        @Query("query") query: String?,           // Para el término de búsqueda por nombre
        @Query("categoryId") categoryId: Long?,   // Para el filtro por ID de categoría
        @Query("ingredients") ingredients: String? // Para IDs de ingredientes, como "1,2,3"
    ): SearchResponse

    @POST("auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): AuthResponse // <--- ¡CAMBIO AQUÍ!

    @POST("auth/signin") // Coincide con POST /api/auth/signin
    suspend fun signIn(@Body request: AuthRequest): AuthResponse

    @POST("auth/reset-password") // Coincide con POST /api/auth/reset-password
    suspend fun resetPassword(@Body request: ResetPasswordRequest): AuthResponse
    @POST("auth/signin-google-token") // Debe coincidir con la ruta en tu backend Node.js
    suspend fun signInWithGoogleToken(@Body request: GoogleSignInTokenRequest): AuthResponse
    @POST("auth/signout") // Coincide con POST /api/auth/signout en tu backend
    suspend fun signOutBackend(): AuthResponse

    @Multipart
    @POST("upload/profile/{authId}")
    suspend fun uploadProfileImage(
        @Path("authId") authId: String,
        @Part file: MultipartBody.Part,
        @Part("email") email: RequestBody
    ): Response<ResponseBody>

    // Nuevo: Endpoint para actualizar la URL de la imagen en la tabla 'usuarios'
    @PATCH("usuarios/{email}") // Asume esta ruta para actualizar un usuario por email
    suspend fun updateUserProfileImage(
        @Path("email") email: String,
        @Body updates: Map<String, String> // Envía un mapa con los campos a actualizar, por ejemplo: {"imagen": "nueva_url"}
    ): retrofit2.Response<Void>

    @Multipart
    @POST("upload/product")
    suspend fun uploadProductImage(@Part image: MultipartBody.Part): ImageUploadResponse

    //categoria
    @GET("categories")
    suspend fun getAllCategories(): List<categoria>

    @POST("categories")
    suspend fun addCategory(@Body category: categoria): categoria

    @DELETE("categories/{id}")
    suspend fun deleteCategory(@Path("id") id: Long)

    //ingrediente
    @GET("ingredientes")
    suspend fun getAllIngredientes(): List<ingrediente>

    @POST("ingredientes")
    suspend fun addIngrediente(@Body ingrediente: ingrediente): ingrediente

    @DELETE("ingredientes/{id}")
    suspend fun deleteIngrediente(@Path("id") id: Long)
    //order
    @GET("orders")
    suspend fun getAllOrdersWithDetails(): List<OrdenWithDetails>

    @PATCH("orders/{id}/status")
    suspend fun updateOrderStatus(@Path("id") orderId: Long, @Body request: UpdateOrderStatusRequest): orden_vendedor
    //producto
    @GET("products")
    suspend fun getAllProducts(): List<producto>

    @POST("products")
    suspend fun addProduct(@Body product: producto): producto

    @GET("products/categories")
    suspend fun getProductCategories(): List<categoria>

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Long): Response<Unit>
    @GET("productos/{id}") // O la ruta correcta para obtener un producto por ID
    suspend fun getProductById(@Path("id") id: Int): producto

    //receta
    @GET("recetas/{id}")
    suspend fun getRecetaByIdWithIngredientes(@Path("id") id: Long): RecetaWithIngredientes // CAMBIO CLAVE AQUÍ
    @GET("recetas/ingredientes-disponibles") // Coincide con GET /api/recetas/ingredientes-disponibles
    suspend fun getAllIngredientesReceta(): List<ingrediente>

    @POST("recetas") // Coincide con POST /api/recetas
    // El Body enviará un objeto con 'recetaData' y 'ingredientesIds'
    suspend fun addReceta(@Body body: AddRecetaRequestBody): receta // Retorna la receta creada

    @GET("recetas") // Coincide con GET /api/recetas
    suspend fun getAllRecetas(): List<receta>

    @DELETE("recetas/{id}") // Coincide con DELETE /api/recetas/:id
    suspend fun deleteReceta(@Path("id") id: Long?): Response<Unit>

    @GET("recetas/ingredientes-relaciones") // Coincide con GET /api/recetas/ingredientes-relaciones
    suspend fun getAllRecetaIngredienteRelations(): List<receta_ingrediente>
    //usuario
    @GET("usuarios")
    suspend fun getAllUsers(): List<usuario>

    @GET("auth/profile-by-email")
    suspend fun getUsuarioByEmail(@Query("correo") email: String): usuario
    //comentario producto
    @GET("comentarios/producto/{idProducto}")
    suspend fun getComentariosByProductoId(@Path("idProducto") idProducto: Int): List<comentario_producto>

    @POST("comentarios")
    suspend fun addComentario(@Body request: AddComentarioRequest): Map<String, String> // O un modelo de respuesta más detallado si el backend lo envía

    //carrito
@POST("carrito")
suspend fun addCartItem(@Body cartItem: carrito): carrito

    @GET("carrito/user/{authId}")
    suspend fun getRawCartItemsForUser(@Path("authId") userId: String): List<carrito>

    @DELETE("carrito/{cartItemId}")
    suspend fun deleteCartEntry(@Path("cartItemId") cartItemId: Long): Unit // <-- ¡Esto es correcto!
    @DELETE("carrito/user/{authId}/clear")
    suspend fun clearUserCart(@Path("authId") userId: String): Unit

    @GET("orders/user/{userId}")
    suspend fun getUserOrders(@Path("userId") userId: String): List<OrdenWithDetails>

    @POST("orders") // Asumo que es "orders" o "orders/create" según la corrección anterior
    suspend fun createOrder(@Body request: CreateOrderRequest): CreateOrderResponse
}

