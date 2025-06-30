package com.Proyecto.coffeepalace.Data.Daos.Busqueda

import com.Proyecto.coffeepalace.Data.Model.receta

interface DaoReceta {
    suspend fun buscarPorNombreOIngrediente(query: String): List<receta>
}
