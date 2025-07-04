// com.Proyecto.coffeepalace.Data.Model/OrdenWithDetails.kt
package com.Proyecto.coffeepalace.Data.Model

import com.google.gson.annotations.SerializedName
import java.util.Date

// Modelo simplificado para el usuario dentro de la factura
// Solo con los campos que necesitamos mostrar.
data class UsuarioOrden(
    val id: Long? = null,
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("correo") val correo: String
)

// Modelo para un ítem en detalle_factura, con el producto anidado
data class DetalleFacturaConProductoCompleto(
    val id: Long? = null,
    @SerializedName("id_factura") val idFactura: Long,
    @SerializedName("id_producto") val idProducto: Long,
    @SerializedName("producto") val producto: Producto?
)

// Modelo para la factura, con sus detalles de factura y AHORA con el usuario anidado
data class FacturaConDetallesCompletos(
    val id: Long? = null,
    @SerializedName("fecha") val fecha: Date,
    @SerializedName("usuarioId") val usuarioId: Long, // Sigue siendo el ID de la FK
    @SerializedName("total_factura") val totalFactura: Double,
    @SerializedName("numero_factura") val numeroFactura: String,
    @SerializedName("usuario") val usuario: UsuarioOrden?, // <--- ¡NUEVO CAMPO! Objeto Usuario anidado
    @SerializedName("detalle_factura") val detallesFactura: List<DetalleFacturaConProductoCompleto>?
)

// Modelo principal para la orden de venta, con la factura anidada
data class OrdenWithDetails(
    val id: Long? = null,
    @SerializedName("id_factura") val idFactura: Long,
    @SerializedName("estado") val estado: String,
    @SerializedName("factura") val factura: FacturaConDetallesCompletos?
)