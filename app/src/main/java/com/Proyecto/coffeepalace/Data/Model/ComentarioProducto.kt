package com.Proyecto.coffeepalace.Data.Model

data class ComentarioProducto(
    val id: Int = 0,
    val idUsuario: Int,
    val idProducto: Int,
    val text: String,
    val rating: Int,
    val timestamp: Long = System.currentTimeMillis()
)


/*data class ComentarioProducto(
    val id: Int = 0,
    val idProducto: Int,
    val idUsuario: Int,
    val text: String,
    val rating: Int,
)
*/

/*import kotlinx.serialization.Serializable

@Serializable
data class ComentarioProducto (
    val id: Int = 0,
    val id_usuario: Int,
    val id_producto: Int,
    val texto: String,
    val calificacion: Int
)
 */