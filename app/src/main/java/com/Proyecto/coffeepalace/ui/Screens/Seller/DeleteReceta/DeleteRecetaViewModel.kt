package com.Proyecto.coffeepalace.ui.Screens.Seller.Receta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.receta.DaoReceta
import com.Proyecto.coffeepalace.Data.Daos.receta.DaoRecetaImpl
import com.Proyecto.coffeepalace.Data.Model.receta
import com.Proyecto.coffeepalace.Data.Model.ingrediente
import com.Proyecto.coffeepalace.Data.Model.RecetaWithIngredientes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeleteRecetaViewModel(
    private val daoReceta: DaoReceta = DaoRecetaImpl()
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
                // 1. Obtener todas las recetas
                val todasLasRecetas = daoReceta.getAllRecetas()
                // 2. Obtener todos los ingredientes
                val todosLosIngredientes = daoReceta.getAllIngredientesReceta()
                // 3. Obtener todas las relaciones de la tabla pivote
                val todasLasRelaciones = daoReceta.getAllRecetaIngredienteRelations()

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
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteReceta(idReceta: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _mensaje.value = null
            try {
                val exito = daoReceta.deleteReceta(idReceta)
                if (exito) {
                    _mensaje.value = "Receta eliminada con éxito."
                    fetchRecetasWithIngredientes() // Vuelve a cargar la lista combinada
                } else {
                    _mensaje.value = "No se pudo eliminar la receta."
                }
            } catch (e: Exception) {
                _mensaje.value = "Error al eliminar receta: ${e.message}"
                println("Error en DeleteRecetaViewModel (deleteReceta): ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearMessage() {
        _mensaje.value = null
    }
}