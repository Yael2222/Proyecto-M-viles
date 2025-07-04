// File: com.Proyecto.coffeepalace.Data.Model/OrdenWithDetails.kt

package com.Proyecto.coffeepalace.Data.Model

import com.google.gson.annotations.SerializedName
import java.util.Date


data class UsuarioOrden(
    val id: Long? = null,
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("correo") val correo: String
)

data class DetalleFacturaConProductoCompleto(
    val id: Long? = null,
    @SerializedName("id_factura") val idFactura: Long,
    @SerializedName("id_producto") val idProducto: Long,
    @SerializedName("producto") val producto: producto?
)

data class FacturaConDetallesCompletos(
    val id: Long? = null,
    @SerializedName("fecha") val fecha: Date,
    @SerializedName("usuario") val usuarioId: Long, // Este mapea al campo 'usuario' (FK) de la DB
    @SerializedName("total_factura") val totalFactura: Double,
    @SerializedName("numero_factura") val numeroFactura: String,
    @SerializedName("usuario_obj") val usuario: UsuarioOrden?, // <-- ¡CAMBIO CLAVE AQUÍ! Mapea al alias 'usuario_obj' del backend
    @SerializedName("detalle_factura") val detallesFactura: List<DetalleFacturaConProductoCompleto>?
)

data class OrdenWithDetails(
    val id: Long? = null,
    @SerializedName("id_factura") val idFactura: Long,
    @SerializedName("estado") val estado: String,
    @SerializedName("factura") val factura: FacturaConDetallesCompletos?
)