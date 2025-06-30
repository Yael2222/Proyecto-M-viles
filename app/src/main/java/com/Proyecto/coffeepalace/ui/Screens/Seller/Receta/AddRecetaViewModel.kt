// com.Proyecto.coffeepalace.ui.Screens.Seller.Receta/AddRecetaViewModel.kt
package com.Proyecto.coffeepalace.ui.Screens.Seller.Receta

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.receta
import com.Proyecto.coffeepalace.Data.Model.ingrediente
import com.Proyecto.coffeepalace.Data.Repository.ProductRepository // Para la subida de imagen (reutilizamos)
import com.Proyecto.coffeepalace.Data.Repository.RecetaRepository // <--- ¡NUEVA IMPORTACIÓN!
import kotlinx.coroutines.launch
import java.io.File
// Recibe los repositorios necesarios
class AddRecetaViewModel(
    private val recetaRepository: RecetaRepository,
    private val productRepository: ProductRepository // Reutilizamos el ProductRepository para la subida de imágenes
) : ViewModel() {
    // private val dao = DaoRecetaImpl() // <-- ¡Elimina esta línea!

    val nombre = mutableStateOf("")
    val descripcion = mutableStateOf("")
    val imagen = mutableStateOf("") // Ahora almacenará la URL pública
    val instrucciones = mutableStateOf("")

    val ingredientesDisponibles = mutableStateListOf<ingrediente>()
    val ingredientesSeleccionados = mutableStateListOf<ingrediente>()

    val mensaje = mutableStateOf("")
    val isSavingReceta = mutableStateOf(false) // Nuevo estado para UI al guardar
    val uploadingImage = mutableStateOf(false) // Nuevo estado para UI al subir imagen

    init {
        cargarIngredientes()
    }

    private fun cargarIngredientes() {
        viewModelScope.launch {
            try {
                val lista = recetaRepository.getAllIngredientesReceta()
                ingredientesDisponibles.clear()
                ingredientesDisponibles.addAll(lista)
            } catch (e: Exception) {
                mensaje.value = "Error al cargar ingredientes disponibles: ${e.message}"
                println("Error al cargar ingredientes disponibles: ${e.message}")
            }
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
        imagen.value = "" // Limpiar la URL de la imagen
        instrucciones.value = ""
        ingredientesSeleccionados.clear()
        mensaje.value = ""
        isSavingReceta.value = false
        uploadingImage.value = false
    }

    // --- Nueva función para subir imagen y luego agregar receta ---
    fun uploadImageAndAddReceta(context: Context, imageLocalUri: Uri?) {
        viewModelScope.launch {
            uploadingImage.value = true
            mensaje.value = "" // Limpiar mensajes anteriores

            var finalImageUrl: String? = null

            if (imageLocalUri != null) {
                try {
                    val tempFile = copyUriToTempFile(context, imageLocalUri)
                    if (tempFile != null) {
                        val mimeType = context.contentResolver.getType(imageLocalUri) ?: "image/*"
                        finalImageUrl = productRepository.uploadImage(tempFile, mimeType) // Reutiliza el ProductRepository
                        tempFile.delete() // Eliminar el archivo temporal
                    }
                } catch (e: Exception) {
                    mensaje.value = "Error al subir la imagen: ${e.message}"
                    println("Error al subir imagen: ${e.message}")
                    uploadingImage.value = false
                    return@launch // Detener si la subida falla
                }
            }
            uploadingImage.value = false

            // Asigna la URL final (puede ser null o vacía si no se subió imagen)
            imagen.value = finalImageUrl ?: "" // Asignar la URL pública o cadena vacía

            // Ahora procede a agregar la receta
            agregarReceta()
        }
    }

    // Función auxiliar para copiar URI a un archivo temporal (igual que en AddProductViewModel)
    private fun copyUriToTempFile(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val tempFile = File.createTempFile("upload_image_", ".tmp", context.cacheDir)
            inputStream?.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            tempFile
        } catch (e: Exception) {
            println("Error copiando URI a archivo temporal: ${e.message}")
            null
        }
    }


    fun agregarReceta() {
        if (nombre.value.isBlank() || descripcion.value.isBlank() || imagen.value.isBlank() || instrucciones.value.isBlank()) {
            mensaje.value = "Por favor, completa todos los campos (incluyendo la imagen)."
            return
        }

        if (ingredientesSeleccionados.isEmpty()) {
            mensaje.value = "Selecciona al menos un ingrediente."
            return
        }

        viewModelScope.launch {
            isSavingReceta.value = true // Indicar que se está guardando
            try {
                val recetaObj = receta(
                    nombre = nombre.value,
                    descripcion = descripcion.value,
                    imagen = imagen.value, // Ya debe ser la URL pública
                    instrucciones = instrucciones.value
                )

                val ingredientesIds = ingredientesSeleccionados.map { it.id ?: 0L } // Usar 0L como fallback para ID nulo

                val exito = recetaRepository.addReceta(recetaObj, ingredientesIds)

                mensaje.value = if (exito) {
                    "Receta agregada con éxito"
                } else {
                    "Error al agregar la receta o sus ingredientes. Revisa los logs."
                }
            } catch (e: Exception) {
                mensaje.value = "Error inesperado al agregar la receta: ${e.message}"
                println("Error inesperado al agregar la receta: ${e.message}")
            } finally {
                isSavingReceta.value = false // Finalizar estado de guardado
            }
        }
    }
}