package com.Proyecto.coffeepalace.di

import com.Proyecto.coffeepalace.Data.Network.ApiService
import com.Proyecto.coffeepalace.Data.Network.NetworkConfig
import com.Proyecto.coffeepalace.Data.Repository.CategoryRepository
import com.Proyecto.coffeepalace.Data.Repository.IngredienteRepository
import com.Proyecto.coffeepalace.Data.Repository.ProductRepository
import com.Proyecto.coffeepalace.Data.Repository.UserRepository
import com.Proyecto.coffeepalace.Data.Repository.RecetaRepository
import com.Proyecto.coffeepalace.Data.Repository.OrderRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Context
import com.Proyecto.coffeepalace.Data.Repository.AuthRepository
import com.Proyecto.coffeepalace.Data.Repository.CheckoutRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.Proyecto.coffeepalace.utils.SessionManager // <--- ¡NUEVA IMPORTACIÓN!


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
    
    val checkoutRepository: CheckoutRepository by lazy {
        CheckoutRepository(apiService)
    }
    private lateinit var _googleSignInOptions: GoogleSignInOptions
    private lateinit var _googleSignInClient: GoogleSignInClient
    private lateinit var _authRepository: AuthRepository
    private lateinit var _sessionManager: SessionManager // <--- ¡NUEVA PROPIEDAD!

    // Función para inicializar las dependencias que requieren contexto
    fun initialize(context: Context) {
        if (!::_googleSignInOptions.isInitialized) {
            _googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("813488748627-rfln2pp5favmhf7np8ql0psfvlu6gj2u.apps.googleusercontent.com")
                .requestEmail()
                .build()
            _googleSignInClient = GoogleSignIn.getClient(context, _googleSignInOptions)
            _sessionManager = SessionManager(context) // <--- ¡INICIALIZA SESSION MANAGER!
            _authRepository = AuthRepository(apiService, _googleSignInClient, _sessionManager) // <--- ¡PASA SESSION MANAGER!
        }
    }

    val authRepository: AuthRepository
        get() = if (::_authRepository.isInitialized) _authRepository else throw IllegalStateException("AppContainer no inicializado. Llama a initialize(context) en tu Application class.")

    val sessionManager: SessionManager // <--- Haz SessionManager accesible
        get() = if (::_sessionManager.isInitialized) _sessionManager else throw IllegalStateException("AppContainer no inicializado. Llama a initialize(context) en tu Application class.")
}