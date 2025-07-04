package com.Proyecto.coffeepalace.Data.Model.Client

import com.Proyecto.coffeepalace.Data.Model.Producto
import com.Proyecto.coffeepalace.Data.Model.Categoria

data class CategoryAndProductsUIModel(
    val category: Categoria,
    val products: List<Producto>
)