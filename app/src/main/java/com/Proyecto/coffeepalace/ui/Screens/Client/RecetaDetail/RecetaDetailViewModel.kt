package com.Proyecto.coffeepalace.ui.Screens.Client.RecetaDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.RecetaWithIngredientes
import com.Proyecto.coffeepalace.Data.Repository.RecetaRepository
// import dagger.hilt.android.lifecycle.HiltViewModel // ELIMINAR ESTA IMPORTACIÓN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
// import javax.inject.Inject // ELIMINAR ESTA IMPORTACIÓN

// ELIMINAR @HiltViewModel
class RecetaDetailViewModel ( // ELIMINAR @Inject y SavedStateHandle
    private val recetaRepository: RecetaRepository
) : ViewModel() {

    private val _recetaDetail = MutableStateFlow<RecetaWithIngredientes?>(null)
    val recetaDetail: StateFlow<RecetaWithIngredientes?> = _recetaDetail.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // No se necesita `init` para `recetaId` si se pasa después
    // init {
    //     val recetaId = savedStateHandle.get<Long>("recetaId")
    //     if (recetaId != null) {
    //         fetchRecetaDetail(recetaId)
    //     } else {
    //         _error.value = "ID de receta no proporcionado."
    //         _isLoading.value = false
    //     }
    // }

    // Nuevo método para establecer el ID y cargar la receta
    fun setRecetaId(recetaId: Long) {
        if (_recetaDetail.value == null || _recetaDetail.value?.receta?.id != recetaId) {
            fetchRecetaDetail(recetaId)
        }
    }

    private fun fetchRecetaDetail(recetaId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val fetchedData = recetaRepository.getRecetaByIdWithIngredientes(recetaId)
                if (fetchedData != null) {
                    _recetaDetail.value = fetchedData
                } else {
                    _error.value = "Receta no encontrada o datos incompletos."
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar los detalles de la receta: ${e.localizedMessage ?: "Error desconocido"}"
                println("Error en RecetaDetailViewModel: ${e.localizedMessage}")
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}