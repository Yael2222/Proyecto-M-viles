package com.Proyecto.coffeepalace.di

import com.Proyecto.coffeepalace.Data.Network.ApiService
import com.Proyecto.coffeepalace.Data.Network.NetworkConfig
import com.Proyecto.coffeepalace.Data.Repository.CategoryRepository
import com.Proyecto.coffeepalace.Data.Repository.IngredienteRepository
import com.Proyecto.coffeepalace.Data.Repository.ProductRepository
import com.Proyecto.coffeepalace.Data.Repository.UserRepository
import com.Proyecto.coffeepalace.Data.Repository.RecetaRepository
import com.Proyecto.coffeepalace.Data.Repository.OrderRepository // <--- ¡NUEVA IMPORTACIÓN!
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppContainer {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    val userRepository: UserRepository by lazy {
        UserRepository(apiService)
    }



    val categoryRepository: CategoryRepository by lazy {
        CategoryRepository(apiService)
    }

    val ingredienteRepository: IngredienteRepository by lazy {
        IngredienteRepository(apiService)
    }
    val productRepository: ProductRepository by lazy {
        ProductRepository(apiService)
    }
    val recetaRepository: RecetaRepository by lazy {
        RecetaRepository(apiService)
    }
    val orderRepository: OrderRepository by lazy {
        OrderRepository(apiService)
    }
}