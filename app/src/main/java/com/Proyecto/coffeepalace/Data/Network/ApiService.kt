package com.Proyecto.coffeepalace.Data.Network

import com.Proyecto.coffeepalace.Data.Model.categoria
import com.Proyecto.coffeepalace.Data.Model.ingrediente
import com.Proyecto.coffeepalace.Data.Model.producto
import com.Proyecto.coffeepalace.Data.Model.usuario
import com.Proyecto.coffeepalace.Data.Model.receta
import com.Proyecto.coffeepalace.Data.Model.receta_ingrediente
import com.Proyecto.coffeepalace.Data.Model.OrdenWithDetails
import com.Proyecto.coffeepalace.Data.Model.orden_vendedor
import retrofit2.http.PATCH
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Multipart
import retrofit2.http.Part

data class UpdateOrderStatusRequest(val estado: String)


interface ApiService {
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

    //receta

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
}
