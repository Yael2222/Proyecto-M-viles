// com.Proyecto.coffeepalace.ui.Screens.Seller.Receta/DeleteRecetaViewModel.kt
package com.Proyecto.coffeepalace.ui.Screens.Seller.Receta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// import com.Proyecto.coffeepalace.Data.Daos.receta.DaoReceta // Ya no se necesita esta interfaz aquí
// import com.Proyecto.coffeepalace.Data.Daos.receta.DaoRecetaImpl // <-- ¡Elimina esta importación!
import com.Proyecto.coffeepalace.Data.Model.RecetaWithIngredientes
import com.Proyecto.coffeepalace.Data.Model.ingrediente // Asegúrate de importar ingrediente
import com.Proyecto.coffeepalace.Data.Model.receta // Asegúrate de importar receta
import com.Proyecto.coffeepalace.Data.Model.receta_ingrediente // Asegúrate de importar receta_ingrediente
import com.Proyecto.coffeepalace.Data.Repository.RecetaRepository // <--- ¡NUEVA IMPORTACIÓN!
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeleteRecetaViewModel(
    // El ViewModel ahora recibe RecetaRepository como parámetro en su constructor.
    private val recetaRepository: RecetaRepository
) : ViewModel() {

    private val _recetasWithIngredientes = MutableStateFlow<List<RecetaWithIngredientes>>(emptyList())
    val recetasWithIngredientes: StateFlow<List<RecetaWithIngredientes>> = _recetasWithIngredientes.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    init {
        fetchRecetasWithIngredientes()
    }

    /**
     * Carga todas las recetas y sus ingredientes asociados.
     */
    fun fetchRecetasWithIngredientes() {
        viewModelScope.launch {
            _isLoading.value = true
            _mensaje.value = null
            try {
                // 1. Obtener todas las recetas usando el repositorio
                val todasLasRecetas = recetaRepository.getAllRecetas()
                // 2. Obtener todos los ingredientes usando el repositorio
                val todosLosIngredientes = recetaRepository.getAllIngredientesReceta()
                // 3. Obtener todas las relaciones de la tabla pivote usando el repositorio
                val todasLasRelaciones = recetaRepository.getAllRecetaIngredienteRelations()

                // Mapear ingredientes por ID para acceso rápido
                val ingredientesMap = todosLosIngredientes.associateBy { it.id }

                // Construir la lista de RecetaWithIngredientes
                val listaCombinada = todasLasRecetas.map { receta ->
                    // Filtrar las relaciones para la receta actual
                    val relacionesParaEstaReceta = todasLasRelaciones.filter { it.id_receta == receta.id }
                    // Obtener los IDs de ingredientes para esta receta
                    val idsIngredientesParaEstaReceta = relacionesParaEstaReceta.map { it.id_ingrediente }
                    // Buscar los objetos ingrediente reales usando el mapa
                    val ingredientesDeEstaReceta = idsIngredientesParaEstaReceta.mapNotNull { ingredientesMap[it] }

                    RecetaWithIngredientes(receta, ingredientesDeEstaReceta)
                }
                _recetasWithIngredientes.value = listaCombinada

            } catch (e: Exception) {
                _mensaje.value = "Error al cargar recetas y sus ingredientes: ${e.message}"
                println("Error en DeleteRecetaViewModel (fetchRecetasWithIngredientes): ${e.message}")
                e.printStackTrace() // Imprimir la traza completa para depuración
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteReceta(idReceta: Long?) {
        viewModelScope.launch {
            _isLoading.value = true
            _mensaje.value = null
            try {
                // Asegúrate de que el ID no es nulo antes de pasarlo
                idReceta?.let {
                    val exito = recetaRepository.deleteReceta(it) // Usa el repositorio
                    if (exito) {
                        _mensaje.value = "Receta eliminada con éxito."
                        fetchRecetasWithIngredientes() // Vuelve a cargar la lista combinada
                    } else {
                        _mensaje.value = "No se pudo eliminar la receta."
                        println("Fallo la eliminación de la receta ID: $it pero no hubo excepción.") // Debugging
                    }
                } ?: run {
                    _mensaje.value = "ID de receta inválido para eliminar."
                }
            } catch (e: Exception) {
                _mensaje.value = "Error al eliminar receta: ${e.message}"
                println("Error en DeleteRecetaViewModel (deleteReceta): ${e.message}")
                e.printStackTrace() // Imprimir la traza completa para depuración
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearMessage() {
        _mensaje.value = null
    }
}