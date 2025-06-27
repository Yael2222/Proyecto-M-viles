package com.Proyecto.coffeepalace.ui.Screens.Seller.ViewUsers

import com.Proyecto.coffeepalace.Data.Daos.usuario.DaoUsuarioImpl
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.usuario.DaoUsuario
import com.Proyecto.coffeepalace.Data.Model.usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewUsersViewModel(
    private val daoUser: DaoUsuario = DaoUsuarioImpl()
) : ViewModel() {

    private val _users = MutableStateFlow<List<usuario>>(emptyList())
    val users: StateFlow<List<usuario>> = _users.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        fetchUsers()
    }


    fun fetchUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val fetchedUsers = daoUser.getAllUsers()
                _users.value = fetchedUsers
            } catch (e: Exception) {
                _error.value = "Error al cargar usuarios: ${e.message}"
                println("Error en ViewUsersViewModel: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}