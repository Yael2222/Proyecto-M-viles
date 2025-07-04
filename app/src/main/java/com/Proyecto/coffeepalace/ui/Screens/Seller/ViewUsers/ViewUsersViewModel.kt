package com.Proyecto.coffeepalace.ui.Screens.Seller.ViewUsers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.usuario
import com.Proyecto.coffeepalace.Data.Repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewUsersViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _users = MutableStateFlow<List<usuario>>(emptyList())
    val users = _users.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _users.value = userRepository.getAllUsers() // Llama al Repository
            } catch (e: Exception) {
                _error.value = e.message
                println("Error cargando usuarios en ViewModel: ${e.message}") // Debug
            } finally {
                _isLoading.value = false
            }
        }
    }
}