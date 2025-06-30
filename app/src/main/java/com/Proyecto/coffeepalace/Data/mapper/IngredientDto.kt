package com.Proyecto.coffeepalace.Data.mapper

import com.Proyecto.coffeepalace.Data.Model.Ingredient
import com.Proyecto.coffeepalace.Data.Remote.dto.IngredientDto

fun IngredientDto.toModel(): Ingredient {
    return Ingredient(
        id = this.id,
        nombre = this.nombre
    )
}
