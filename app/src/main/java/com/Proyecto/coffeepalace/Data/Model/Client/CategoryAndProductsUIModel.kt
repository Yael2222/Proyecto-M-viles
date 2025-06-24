package com.Proyecto.coffeepalace.Data.Model.Client

import com.Proyecto.coffeepalace.Data.Model.Producto
import com.Proyecto.coffeepalace.Data.Model.categoria

data class CategoryAndProductsUIModel(
    val category: categoria,
    val products: List<Producto>
)