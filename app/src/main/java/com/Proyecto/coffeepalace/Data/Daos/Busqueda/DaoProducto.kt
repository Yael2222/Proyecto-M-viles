package com.Proyecto.coffeepalace.Data.Daos.Busqueda

import com.Proyecto.coffeepalace.Data.Model.producto

interface DaoProducto {
    suspend fun buscarPorNombreOIngrediente(query: String): List<producto>
}
