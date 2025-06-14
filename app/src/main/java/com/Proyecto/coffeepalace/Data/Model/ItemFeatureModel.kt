package com.Proyecto.coffeepalace.Data.Model

import com.Proyecto.coffeepalace.ui.Screens.HomeFiltered.FilteredTypes

data class ItemFeatureModel(
    val id: Int,
    val title: String,
    val category: String,
    val image: String
)

fun ItemFeatureModel.toFilteredType() = FilteredTypes.fromId(id = id)