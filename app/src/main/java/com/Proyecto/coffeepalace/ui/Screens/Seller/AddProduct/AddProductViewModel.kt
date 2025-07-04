// com.Proyecto.coffeepalace.ui.Screens.Seller.AddProduct/AddProductViewModel.kt
package com.Proyecto.coffeepalace.ui.Screens.Seller.AddProduct

import android.content.Context // <--- Nueva importación para usar el contexto
import android.net.Uri // <--- Nueva importación
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Model.Categoria
import com.Proyecto.coffeepalace.Data.Model.Producto
import com.Proyecto.coffeepalace.Data.Repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File // <--- Nueva importación

class AddProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    val name = MutableStateFlow("")
    val price = MutableStateFlow("")
    val description = MutableStateFlow("")
    val imageUri = MutableStateFlow<String?>(null) // Esto ahora almacenará la URL PÚBLICA
    val selectedCategoryId = MutableStateFlow<Long?>(null)

    private val _categorias = MutableStateFlow<List<Categoria>>(emptyList())
    val categorias = _categorias.asStateFlow()

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess = _saveSuccess.asStateFlow()

    private val _uploadingImage = MutableStateFlow(false) // Nuevo estado para indicar si se está subiendo la imagen
    val uploadingImage = _uploadingImage.asStateFlow()

    init {
        loadCategorias()
    }

    fun loadCategorias() {
        viewModelScope.launch {
            val result = productRepository.getCategoriasProducto()
            println("CATEGORIAS CARGADAS: $result")
            _categorias.value = result
            if (selectedCategoryId.value == null && result.isNotEmpty()) {
                selectedCategoryId.value = result.first().id
            }
        }
    }

    // --- Función para subir la imagen antes de guardar el producto ---
    // Recibe el Context y el Uri de la imagen seleccionada.
    fun uploadAndSaveProduct(context: Context, imageLocalUri: Uri?) {
        viewModelScope.launch {
            _uploadingImage.value = true // Iniciar estado de subida
            _saveSuccess.value = false // Resetear el estado de éxito

            var finalImageUrl: String? = null

            if (imageLocalUri != null) {
                try {
                    // Copiar la URI local a un archivo temporal para poder enviarla
                    val tempFile = copyUriToTempFile(context, imageLocalUri)
                    if (tempFile != null) {
                        val mimeType = context.contentResolver.getType(imageLocalUri) ?: "image/*"
                        // Llamar al repositorio para subir la imagen al backend
                        finalImageUrl = productRepository.uploadImage(tempFile, mimeType)
                        tempFile.delete() // Eliminar el archivo temporal después de subir
                    }
                } catch (e: Exception) {
                    println("Error al subir imagen: ${e.message}")
                    // Aquí podrías actualizar un MutableStateFlow para mostrar un error en la UI
                    _uploadingImage.value = false
                    return@launch // Detener si la subida de imagen falla
                }
            }

            // Si la subida fue exitosa (o no había imagen para subir), procede a guardar el producto
            // Asigna la URL final (puede ser null si no se subió imagen)
            imageUri.value = finalImageUrl

            // Ahora llama a la lógica existente para guardar el producto
            saveProduct()
            _uploadingImage.value = false // Finalizar estado de subida
        }
    }

    // Helper function to copy URI content to a temporary file
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


    // saveProduct() ahora solo se encarga de los datos del producto (sin la imagen en sí)
    fun saveProduct() {
        viewModelScope.launch {
            val priceValue = price.value.toDoubleOrNull()
            val categoryId = selectedCategoryId.value

            if (name.value.isBlank() || priceValue == null || description.value.isBlank() || categoryId == null) {
                println("Invalid input: Please fill all fields correctly.")
                return@launch
            }

            val productToSave = Producto(
                nombre = name.value,
                descripcion = description.value,
                imagen = imageUri.value ?: "", // Usar la URL que ya está en imageUri.value
                precio = priceValue,
                categoria = categoryId
            )

            _saveSuccess.value = productRepository.addProducto(productToSave)
            // Si el guardado es exitoso, los campos se resetearán en el diálogo de éxito.
        }
    }

    fun resetForm() {
        name.value = ""
        price.value = ""
        description.value = ""
        selectedCategoryId.value = null
        imageUri.value = null // Limpiar la URL de la imagen también
        _saveSuccess.value = false
        _uploadingImage.value = false // Resetear estado de subida
        loadCategorias()
    }
}