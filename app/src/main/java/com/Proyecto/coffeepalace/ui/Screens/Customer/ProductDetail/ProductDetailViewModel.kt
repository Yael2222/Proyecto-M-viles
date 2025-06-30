package com.Proyecto.coffeepalace.ui.Screens.Customer.ProductDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.ComentarioProducto
import com.Proyecto.coffeepalace.Data.Repository.ComentarioProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val repository: ComentarioProductoRepository
) : ViewModel() {

    private val _comments = MutableStateFlow<List<ComentarioProducto>>(emptyList())
    val comments: StateFlow<List<ComentarioProducto>> = _comments.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _newCommentText = MutableStateFlow("")
    val newCommentText: StateFlow<String> = _newCommentText.asStateFlow()

    private val _newCommentRating = MutableStateFlow(5)
    val newCommentRating: StateFlow<Int> = _newCommentRating.asStateFlow()

    fun cargarComentarios(idProducto: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Sincronizar con Supabase primero
                repository.syncComentarios(idProducto)

                // Observar cambios en Room
                repository.getComentariosByProducto(idProducto).collect { comentarios ->
                    _comments.value = comentarios
                }
            } catch (e: Exception) {
                println("Error loading comments: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun agregarComentario(idProducto: Int, idUsuario: Int) {
        if (_newCommentText.value.isBlank()) return

        viewModelScope.launch {
            try {
                val comentario = ComentarioProducto(
                    idProducto = idProducto,
                    idUsuario = idUsuario,
                    text = _newCommentText.value.trim(),
                    rating = _newCommentRating.value
                )

                repository.insertComentario(comentario)

                // Limpiar campos
                _newCommentText.value = ""
                _newCommentRating.value = 5
            } catch (e: Exception) {
                println("Error adding comment: ${e.message}")
            }
        }
    }

    fun updateCommentText(text: String) {
        _newCommentText.value = text
    }

    fun updateRating(rating: Int) {
        _newCommentRating.value = rating
    }
}






/*import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.Busqueda.DaoProductoImpl
import com.Proyecto.coffeepalace.Data.Model.producto
import com.Proyecto.coffeepalace.Data.Repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val repository: ProductRepository = ProductRepository(DaoProductoImpl())
) : ViewModel() {

    private val _producto = MutableStateFlow<producto?>(null)
    val producto: StateFlow<producto?> = _producto

    fun loadProducto(id: Int) {
        viewModelScope.launch {
            val result = repository.getProductoById(id)
            _producto.value = result
        }
    }
}
 */






/*import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.Proyecto.coffeepalace.Data.Model.ComentarioProducto
import com.Proyecto.coffeepalace.Data.Repository.ComentarioRepository
import kotlinx.coroutines.flow.asStateFlow


class ProductViewModel(private val repository: ComentarioRepository) : ViewModel() {

    private val _comments = MutableStateFlow<List<ComentarioProducto>>(emptyList())
    val comments: StateFlow<List<ComentarioProducto>> = _comments.asStateFlow()

    var newCommentText = MutableStateFlow("")
    var newRating = MutableStateFlow(0)

    // Función para cargar comentarios de un producto dado
    fun cargarComentarios(idProducto: Int) {
        viewModelScope.launch {
            val lista = repository.obtenerComentarios(idProducto)
            _comments.value = lista
        }
    }

    // Función para agregar un comentario nuevo y recargar la lista
    fun agregarComentario(comentario: ComentarioProducto, idProducto: Int) {
        viewModelScope.launch {
            val exito = repository.agregarComentario(comentario)
            if (exito) {
                cargarComentarios(idProducto)
                newCommentText.value = ""
                newRating.value = 0
            }
        }
    }
}
 */



/*
class ProductViewModel(private val repository: CommentRepository) : ViewModel() {
    var newCommentText = MutableStateFlow("")
    var newRating = MutableStateFlow(0)

    val comments: StateFlow<List<Comment>> = repository.getAllComments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addComment() {
        val text = newCommentText.value
        val rating = newRating.value
        if (text.isNotBlank() && rating > 0) {
            viewModelScope.launch {
                repository.insertComment(Comment(text = text, rating = rating))
                newCommentText.value = ""
                newRating.value = 0
            }
        }
    }
}*/

