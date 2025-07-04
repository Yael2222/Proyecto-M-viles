package com.Proyecto.coffeepalace.ui.Screens.User

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.OrdenWithDetails // <--- CAMBIO AQUÍ: Usamos el modelo anidado
import com.Proyecto.coffeepalace.Data.Repository.AuthRepository
import com.Proyecto.coffeepalace.Data.Repository.OrderRepository
import com.Proyecto.coffeepalace.Data.Repository.UserRepository
import com.Proyecto.coffeepalace.Data.Repository.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

class UserViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(authRepository.isLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail.asStateFlow()

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName.asStateFlow()

    private val _userImage = MutableStateFlow<String?>(null)
    val userImage: StateFlow<String?> = _userImage.asStateFlow()

    private val _logoutMessage = MutableStateFlow<String?>(null)
    val logoutMessage: StateFlow<String?> = _logoutMessage.asStateFlow()

    private val _showSnackbar = MutableStateFlow<Pair<String, Boolean>?>(null)
    val showSnackbar: StateFlow<Pair<String, Boolean>?> = _showSnackbar.asStateFlow()

    private val _userOrders = MutableStateFlow<List<OrdenWithDetails>>(emptyList()) // <--- CAMBIO AQUÍ: Tipo a OrdenWithDetails
    val userOrders: StateFlow<List<OrdenWithDetails>> = _userOrders.asStateFlow()

    private val _isLoadingOrders = MutableStateFlow(false)
    val isLoadingOrders: StateFlow<Boolean> = _isLoadingOrders.asStateFlow()


    init {
        if (_isLoggedIn.value) {
            loadUserData()
            loadUserProfileImage()
            loadUserOrders()
        }
    }

    fun loadUserData() {
        _isLoggedIn.value = authRepository.isLoggedIn()
        if (_isLoggedIn.value) {
            _userEmail.value = authRepository.getCurrentUserEmail()
            _userName.value = authRepository.getCurrentUserName()
        } else {
            _userEmail.value = null
            _userName.value = null
        }
    }

    fun loadUserProfileImage() {
        viewModelScope.launch {
            val email = authRepository.getCurrentUserEmail()
            if (email != null) {
                try {
                    // Asegúrate de que getUsuarioProfileFromBackend devuelva un objeto con una propiedad 'imagen'
                    val userProfile = authRepository.getUsuarioProfileFromBackend(email)
                    _userImage.value = userProfile?.imagen
                    Log.d("UserViewModel", "Imagen de perfil cargada: ${userProfile?.imagen}")
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error al cargar la imagen de perfil: ${e.message}", e)
                    _userImage.value = null
                }
            } else {
                _userImage.value = null
            }
        }
    }

    fun uploadProfileImage(imageUri: Uri) {
        viewModelScope.launch {
            _showSnackbar.value = Pair("Subiendo imagen...", false)

            val authId = authRepository.getCurrentAuthId() // Asume que devuelve String?
            val currentUserEmail = authRepository.getCurrentUserEmail()

            if (authId == null || currentUserEmail == null) {
                _showSnackbar.value = Pair("Error: No se encontró la información del usuario.", true)
                return@launch
            }

            try {
                when (val uploadResult = userRepository.uploadProfileImage(authId, imageUri)) {
                    is Result.Success -> {
                        val imageUrl = uploadResult.data
                        Log.d("UserViewModel", "Imagen subida exitosamente. URL: $imageUrl")

                        val success = userRepository.updateUserProfileImage(currentUserEmail, imageUrl)

                        if (success) {
                            loadUserProfileImage()
                            _showSnackbar.value = Pair("Imagen de perfil actualizada.", false)
                        } else {
                            _showSnackbar.value = Pair("Error al actualizar la URL en la base de datos.", true)
                        }
                    }
                    is Result.Error -> {
                        val errorMessage = uploadResult.exception.message ?: "Error desconocido al subir la imagen."
                        _showSnackbar.value = Pair("Error al subir la imagen: $errorMessage", true)
                        Log.e("UserViewModel", "Fallo al subir imagen: $errorMessage", uploadResult.exception)
                    }
                    is Result.Loading -> {
                        Log.d("UserViewModel", "Image upload in progress...")
                    }
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error general al subir/actualizar imagen de perfil: ${e.message}", e)
                _showSnackbar.value = Pair("Error: ${e.message}", true)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            val success = authRepository.signOut()
            if (success) {
                _isLoggedIn.value = false
                _userEmail.value = null
                _userName.value = null
                _userImage.value = null
                _logoutMessage.value = "Sesión cerrada exitosamente."
                _userOrders.value = emptyList()
            } else {
                _logoutMessage.value = "Error al cerrar sesión."
            }
        }
    }

    fun clearLogoutMessage() {
        _logoutMessage.value = null
    }

    fun clearSnackbarMessage() {
        _showSnackbar.value = null
    }

    fun loadUserOrders() {
        viewModelScope.launch {
            _isLoadingOrders.value = true
            val userId = authRepository.getCurrentAuthId() // Asume que devuelve String?
            if (userId == null) {
                _userOrders.value = emptyList()
                _showSnackbar.value = Pair("Inicia sesión para ver tus pedidos.", true)
            } else {
                try {
                    val orders = orderRepository.getUserOrders(userId)
                    _userOrders.value = orders
                    Log.d("UserViewModel", "Órdenes cargadas: ${orders.size}")
                } catch (e: Exception) {
                    _showSnackbar.value = Pair("Error al cargar los pedidos: ${e.localizedMessage}", true)
                    _userOrders.value = emptyList()
                    Log.e("UserViewModel", "Error loading user orders: ${e.localizedMessage}", e)
                }
            }
            _isLoadingOrders.value = false
        }
    }

    // Factoría para el ViewModel
    class UserViewModelFactory(
        private val authRepository: AuthRepository, // FIRST parameter: AuthRepository
        private val userRepository: UserRepository, // SECOND parameter: UserRepository
        private val orderRepository: OrderRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            // ... ensures these parameters are passed in the correct order to UserViewModel
            return UserViewModel(authRepository, userRepository, orderRepository) as T
        }
    }
}