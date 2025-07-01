package com.Proyecto.coffeepalace.ui.Screens.AdminCommentsPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.comentarioProd.DaoComentario
import com.Proyecto.coffeepalace.Data.Daos.comentarioProd.DaoComentarioImpl
import com.Proyecto.coffeepalace.Data.Daos.usuario.DaoUsuario
import com.Proyecto.coffeepalace.Data.Daos.usuario.DaoUsuarioImpl
import com.Proyecto.coffeepalace.Data.Daos.producto.DaoProducto
import com.Proyecto.coffeepalace.Data.Daos.producto.DaoProductoImpl
import com.Proyecto.coffeepalace.Data.Model.ComentarioConNombreUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewCommentsWithUsersViewModel(
    private val daoComentario: DaoComentario = DaoComentarioImpl(),
    private val daoUsuario: DaoUsuario = DaoUsuarioImpl(),
    private val daoProducto: DaoProducto = DaoProductoImpl()
) : ViewModel() {

    private val _comentariosConUsuario = MutableStateFlow<List<ComentarioConNombreUsuario>>(emptyList())
    val comentariosConUsuario: StateFlow<List<ComentarioConNombreUsuario>> = _comentariosConUsuario.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        fetchCommentsAndUsers()
    }

    private fun fetchCommentsAndUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val usuarios = daoUsuario.getAllUsers()
                val comentarios = daoComentario.getAllComentarios()
                val productos = daoProducto.getAllProductos()
                val usuariosMap = usuarios.associateBy({ it.id }, { it.nombre ?: "Sin nombre" })
                val productosMap = productos.associateBy({ it.id })

                val combinados = comentarios.map { comment ->
                    ComentarioConNombreUsuario(
                        comentario = comment,
                        nombreUsuario = usuariosMap[comment.id_usuario] ?: "Desconocido",
                        nombreProducto = productosMap[comment.id_producto]?.nombre ?: "Desconocido"
                    )
                }
                _comentariosConUsuario.value = combinados
            } catch (e: Exception) {
                _error.value = "Error al cargar datos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
