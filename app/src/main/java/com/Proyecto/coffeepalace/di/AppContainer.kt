package com.Proyecto.coffeepalace.di

import android.content.Context
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
import com.Proyecto.coffeepalace.Data.Repository.AuthRepository
import com.Proyecto.coffeepalace.Data.Repository.ComentariosRepository
import com.Proyecto.coffeepalace.Data.Repository.SearchRepository
import com.Proyecto.coffeepalace.Data.Repository.ShoppingCartRepository
import com.Proyecto.coffeepalace.ui.Screens.User.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.Proyecto.coffeepalace.utils.SessionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object AppContainer {

    // Se inicializa solo una vez de forma lazy, no necesita el context para su construcción inicial
    private val retrofit: Retrofit by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Para logs detallados de peticiones y respuestas
        }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    private lateinit var _applicationContext: Context // Renombrado para evitar confusión con el parámetro

    // Propiedades que requieren inicialización con Context
    private lateinit var _googleSignInOptions: GoogleSignInOptions
    private lateinit var _googleSignInClient: GoogleSignInClient
    private lateinit var _sessionManager: SessionManager
    private lateinit var _authRepository: AuthRepository // Depende de _sessionManager y _googleSignInClient


    /**
     * Inicializa las dependencias que requieren un Context.
     * Debe llamarse una única vez al inicio de la aplicación (ej. en Application.onCreate()).
     */
    fun initialize(context: Context) {
        // Inicializa el contexto de la aplicación si aún no lo está
        if (!::_applicationContext.isInitialized) {
            _applicationContext = context.applicationContext
        }

        // Inicializa SessionManager si aún no lo está
        if (!::_sessionManager.isInitialized) {
            _sessionManager = SessionManager(_applicationContext)
        }

        // Inicializa Google Sign-In y AuthRepository si aún no lo están
        if (!::_googleSignInOptions.isInitialized) {
            _googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("813488748627-rfln2pp5favmhf7np8ql0psfvlu6gj2u.apps.googleusercontent.com")
                .requestEmail()
                .build()
            _googleSignInClient = GoogleSignIn.getClient(_applicationContext, _googleSignInOptions)
            // _sessionManager ya debería estar inicializado en este punto
            _authRepository = AuthRepository(apiService, _googleSignInClient, _sessionManager)
        }
    }

    // --- Servicios API (Lazy Initialization) ---

    // Esta es tu única instancia de ApiService que contendrá todas las llamadas
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    // --- Repositorios (Lazy Initialization) ---

    // Repositorios que solo necesitan apiService
    val comentariosRepository: ComentariosRepository by lazy { ComentariosRepository(apiService) }
    val categoryRepository: CategoryRepository by lazy { CategoryRepository(apiService) }
    val ingredienteRepository: IngredienteRepository by lazy { IngredienteRepository(apiService) }
    val productRepository: ProductRepository by lazy { ProductRepository(apiService) }
    val recetaRepository: RecetaRepository by lazy { RecetaRepository(apiService) }
    val orderRepository: OrderRepository by lazy { OrderRepository(apiService) }
    val searchRepository: SearchRepository by lazy { SearchRepository(apiService, ingredienteRepository, categoryRepository) }
    val shoppingCartRepository: ShoppingCartRepository by lazy {
        ShoppingCartRepository(apiService, productRepository)
    }
    val userViewModelFactory: UserViewModel.UserViewModelFactory by lazy {
        UserViewModel.UserViewModelFactory(
            authRepository,    // <--- Pass authRepository FIRST
            userRepository,    // <--- Pass userRepository SECOND
            orderRepository
        )
    }
    // Repositorios que requieren el contexto o SessionManager
    val userRepository: UserRepository by lazy {
        // Asegurarse de que initialize() fue llamado
        if (!::_applicationContext.isInitialized || !::_sessionManager.isInitialized) {
            throw IllegalStateException("AppContainer no inicializado completamente. Llama a initialize(context) en tu Application class antes de acceder a UserRepository.")
        }
        UserRepository(apiService, _applicationContext, _sessionManager)
    }

    // --- Getters para propiedades inicializadas con el Context ---
    // (Aseguran que se llamo a initialize() antes de acceder)

    val authRepository: AuthRepository
        get() {
            if (!::_authRepository.isInitialized) {
                throw IllegalStateException("AppContainer.authRepository no inicializado. Llama a initialize(context) en tu Application class antes de acceder a AuthRepository.")
            }
            return _authRepository
        }

    val sessionManager: SessionManager
        get() {
            if (!::_sessionManager.isInitialized) {
                throw IllegalStateException("AppContainer.sessionManager no inicializado. Llama a initialize(context) en tu Application class antes de acceder a SessionManager.")
            }
            return _sessionManager
        }

    // Si otras partes de la app necesitan acceso al contexto de aplicación, puedes añadir un getter aquí
    val applicationContext: Context
        get() {
            if (!::_applicationContext.isInitialized) {
                throw IllegalStateException("AppContainer.applicationContext no inicializado. Llama a initialize(context) en tu Application class.")
            }
            return _applicationContext
        }
}