package com.Proyecto.coffeepalace.ui.Screens.AdminPOPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.usuario.DaoUsuario
import com.Proyecto.coffeepalace.Data.Daos.usuario.DaoUsuarioImpl
import com.Proyecto.coffeepalace.Data.Daos.factura.DaoFactura
import com.Proyecto.coffeepalace.Data.Daos.factura.DaoFacturaImpl
import com.Proyecto.coffeepalace.Data.Daos.orden.DaoOrdenVendedor
import com.Proyecto.coffeepalace.Data.Daos.orden.DaoOrdenVendedorImpl
import com.Proyecto.coffeepalace.Data.Daos.detalleDeFactura.DaoDetalleDeFactura
import com.Proyecto.coffeepalace.Data.Daos.detalleDeFactura.DaoDetalleDeFacturaImpl
import com.Proyecto.coffeepalace.Data.Daos.producto.DaoProducto
import com.Proyecto.coffeepalace.Data.Daos.producto.DaoProductoImpl
import com.Proyecto.coffeepalace.Data.Model.FacturaConNombreUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewFacturasViewModel(
    private val daoUsuario: DaoUsuario = DaoUsuarioImpl(),
    private val daoFactura: DaoFactura = DaoFacturaImpl(),
    private val daoOrdenVendedor: DaoOrdenVendedor = DaoOrdenVendedorImpl(),
    private val daoDetalle: DaoDetalleDeFactura = DaoDetalleDeFacturaImpl(),
    private val daoProducto: DaoProducto = DaoProductoImpl()
) : ViewModel() {
    private val _facturaConNombreUsuario = MutableStateFlow<List<FacturaConNombreUsuario>>(emptyList())
    val facturaConNombreUsuario: StateFlow<List<FacturaConNombreUsuario>> = _facturaConNombreUsuario.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        fetchFacturaAndUsers()
    }

    private fun fetchFacturaAndUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val usuarios = daoUsuario.getAllUsers()
                val facturas = daoFactura.getAllFacturas()
                val ordenes = daoOrdenVendedor.getAllOrdenVendedor()
                val detalles = daoDetalle.getAllDetalles()
                val productos = daoProducto.getAllProductos()

                val usuariosMap = usuarios.associateBy({ it.id }, { it.nombre ?: "Sin nombre" })
                val ordenesMap = ordenes.associateBy({ it.id_factura }, { it.estado })
                val productosMap = productos.associateBy { it.id }

                val combinadosFU = facturas.map { factura ->

                    val detallesDeEstaFactura = detalles.filter { it.id_factura == factura.id }
                    val productosFactura = detallesDeEstaFactura.mapNotNull { detalle ->
                        productosMap[detalle.id_producto]
                    }

                    FacturaConNombreUsuario(
                        factura = factura,
                        nombreUsuario = usuariosMap[factura.usuarioId] ?: "Desconocido",
                        estadoOrden = ordenesMap[factura.id] ?: "Desconocido",
                        productos = productosFactura
                    )
                }
                _facturaConNombreUsuario.value = combinadosFU
            } catch (e: Exception) {
                _error.value = "Error al cargar datos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}