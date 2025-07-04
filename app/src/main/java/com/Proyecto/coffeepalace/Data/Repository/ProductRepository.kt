package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Daos.product.DaoProducto
import com.Proyecto.coffeepalace.Data.Model.Categoria
import com.Proyecto.coffeepalace.Data.Model.Producto
import com.Proyecto.coffeepalace.Data.Network.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull

class ProductRepository(private val apiService: ApiService) : DaoProducto {

    suspend fun uploadImage(file: File, mimeType: String): String? {
        return try {
            val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())

            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

            val response = apiService.uploadProductImage(body)
            response.url
        } catch (e: Exception) {
            println("Error uploading image to backend: ${e.message}")
            null
        }
    }

    override suspend fun addProducto(producto: Producto): Boolean {
        return try {
            val response = apiService.addProduct(producto)
            response.id != null
        } catch (e: Exception) {
            println("Error adding product to backend: ${e.message}")
            false
        }
    }

    override suspend fun getCategoriasProducto(): List<Categoria> {
        return try {
            apiService.getProductCategories()
        } catch (e: Exception) {
            println("Error fetching product categories from backend: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getProductos(): List<Producto> {
        return try {
            apiService.getAllProducts()
        } catch (e: Exception) {
            println("Error fetching products from backend: ${e.message}")
            emptyList()
        }
    }

    override suspend fun deleteProducto(id: Long?): Boolean {
        return try {
            id?.let {
                val response = apiService.deleteProduct(it) // Recibe Response<Unit>
                if (response.isSuccessful) { // Verifica si la respuesta HTTP fue exitosa (2xx)
                    true
                } else {
                    println("Error HTTP al eliminar producto: ${response.code()} - ${response.errorBody()?.string()}")
                    false
                }
            } ?: false
        } catch (e: Exception) {
            println("Error al eliminar el producto en ProductRepository (excepción de red/parseo): ${e.message}")
            e.printStackTrace()
            false
        }
    }

    override suspend fun getAllProducts(): List<Producto> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsByCategory(categoryId: Long): List<Producto> {
        TODO("Not yet implemented")
    }
}