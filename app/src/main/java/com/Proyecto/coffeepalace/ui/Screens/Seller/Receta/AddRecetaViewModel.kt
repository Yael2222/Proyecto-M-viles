package com.Proyecto.coffeepalace.ui.Screens.Seller.Receta

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.receta.DaoRecetaImpl
import com.Proyecto.coffeepalace.Data.Model.receta
import com.Proyecto.coffeepalace.Data.Model.ingrediente
import kotlinx.coroutines.launch

class AddRecetaViewModel : ViewModel() {
    private val dao = DaoRecetaImpl()

    val nombre = mutableStateOf("")
    val descripcion = mutableStateOf("")
    val imagen = mutableStateOf("")
    val instrucciones = mutableStateOf("")

    val ingredientesDisponibles = mutableStateListOf<ingrediente>()
    val ingredientesSeleccionados = mutableStateListOf<ingrediente>()

    val mensaje = mutableStateOf("")

    init {
        cargarIngredientes()
    }

    private fun cargarIngredientes() {
        viewModelScope.launch {
            val lista = dao.getAllIngredientesReceta()
            ingredientesDisponibles.clear()
            ingredientesDisponibles.addAll(lista)
        }
    }

    fun alternarSeleccion(ingrediente: ingrediente) {
        if (ingredientesSeleccionados.contains(ingrediente)) {
            ingredientesSeleccionados.remove(ingrediente)
        } else {
            ingredientesSeleccionados.add(ingrediente)
        }
    }

    fun limpiarFormulario() {
        nombre.value = ""
        descripcion.value = ""
        imagen.value = ""
        instrucciones.value = ""
        ingredientesSeleccionados.clear()
        mensaje.value = ""
    }

    fun agregarReceta() {
        if (nombre.value.isBlank() || descripcion.value.isBlank() || imagen.value.isBlank() || instrucciones.value.isBlank()) {
            mensaje.value = "Por favor, completa todos los campos."
            return
        }

        if (ingredientesSeleccionados.isEmpty()) {
            mensaje.value = "Selecciona al menos un ingrediente."
            return
        }

        viewModelScope.launch {
            val recetaObj = receta(
                id = 0L,
                nombre = nombre.value,
                descripcion = descripcion.value,
                imagen = imagen.value,
                instrucciones = instrucciones.value
            )

            val ingredientesIds = ingredientesSeleccionados.map { it.id }

            val exito = dao.addReceta(recetaObj, ingredientesIds)

            mensaje.value = if (exito) {
                "Receta agregada con éxito"
            } else {
                "Error al agregar la receta o sus ingredientes."
            }
        }
    }
}