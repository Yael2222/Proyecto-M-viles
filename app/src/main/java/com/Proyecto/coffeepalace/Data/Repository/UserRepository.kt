package com.Proyecto.coffeepalace.Data.Repository

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.webkit.MimeTypeMap
import com.Proyecto.coffeepalace.Data.Daos.usuario.DaoUsuario
import com.Proyecto.coffeepalace.Data.Model.usuario
import com.Proyecto.coffeepalace.Data.Network.ApiService
import com.Proyecto.coffeepalace.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

// Asegúrate de que tu sealed class Result está accesible aquí,
// por ejemplo, si está en un archivo llamado Result.kt en el mismo paquete, o importada.
// Si no está en el mismo paquete, añade: import tu.paquete.Result

class UserRepository(
    private val apiService: ApiService,
    private val context: Context,
    private val sessionManager: SessionManager
) : DaoUsuario {

    override suspend fun getAllUsers(): List<usuario> {
        return try {
            apiService.getAllUsers()
        } catch (e: Exception) {
            Log.e("UserRepository", "Error fetching users from backend: ${e.message}")
            emptyList()
        }
    }


    suspend fun uploadProfileImage(authId: String, imageUri: Uri): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val userEmail = sessionManager.getUserEmail()

                if (userEmail == null) {
                    // Cuidado: Si tu Error no acepta Throwable, cambia 'exception: Throwable' en tu Result.Error
                    // Si acepta Throwable, está bien así.
                    Result.Error(Exception("No se pudo obtener el email del usuario. Asegúrate de que el usuario está logueado."))
                } else {
                    val file = uriToFile(imageUri, context)
                    val requestFile = RequestBody.create(
                        context.contentResolver.getType(imageUri)?.toMediaTypeOrNull(),
                        file
                    )
                    val filePart = MultipartBody.Part.createFormData("image", file.name, requestFile)
                    val emailPart = RequestBody.create("text/plain".toMediaTypeOrNull(), userEmail)

                    val response = apiService.uploadProfileImage(authId, filePart, emailPart)

                    if (response.isSuccessful) {
                        val responseBody = response.body()?.string()
                        if (responseBody != null) {
                            val jsonObject = JSONObject(responseBody)
                            val imageUrl = jsonObject.getString("url")
                            Log.d("UserRepository", "Imagen subida exitosamente. URL: $imageUrl")
                            Result.Success(imageUrl) // Usar tu constructor Success
                        } else {
                            Result.Error(Exception("Respuesta vacía del servidor al subir la imagen.")) // Usar tu constructor Error
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("UserRepository", "Fallo al subir imagen: ${response.code()} - $errorBody")
                        Result.Error(Exception("Fallo al subir imagen: ${response.code()} - $errorBody")) // Usar tu constructor Error
                    }
                }
            } catch (e: Exception) {
                Log.e("UserRepository", "Error en uploadProfileImage: ${e.message}", e)
                Result.Error(e) // Usar tu constructor Error
            }
        }
    }

    suspend fun updateUserProfileImage(userEmail: String, imageUrl: String): Boolean {
        return try {
            val response = apiService.updateUserProfileImage(userEmail, mapOf("imagen" to imageUrl))

            if (response.isSuccessful) {
                Log.d("UserRepository", "URL de imagen de perfil actualizada en la DB para $userEmail")
                true
            } else {
                Log.e("UserRepository", "Fallo al actualizar URL de imagen: ${response.code()} - ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Excepción al actualizar URL de imagen de perfil: ${e.message}", e)
            false
        }
    }

    private fun uriToFile(uri: Uri, context: Context): File {
        val contentResolver = context.contentResolver
        val fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri)) ?: "jpg"
        val fileName = "${UUID.randomUUID()}.$fileExtension"
        val file = File(context.cacheDir, fileName)

        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return file
    }

    private fun getFileName(uri: Uri): String? {
        var name: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    name = it.getString(nameIndex)
                }
            }
        }
        return name
    }
}