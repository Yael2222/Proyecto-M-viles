package com.Proyecto.coffeepalace.Data.Network

import com.Proyecto.coffeepalace.Data.Model.categoria
import com.Proyecto.coffeepalace.Data.Model.ingrediente
import com.Proyecto.coffeepalace.Data.Model.producto
import com.Proyecto.coffeepalace.Data.Model.usuario
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Multipart
import retrofit2.http.Part

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
    //usuario
    @GET("usuarios")
    suspend fun getAllUsers(): List<usuario>
}
