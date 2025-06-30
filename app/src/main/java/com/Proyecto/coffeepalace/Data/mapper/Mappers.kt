package com.Proyecto.coffeepalace.Data.mapper

import com.Proyecto.coffeepalace.Data.Model.Ingredient
import com.Proyecto.coffeepalace.Data.Model.Product
import com.Proyecto.coffeepalace.Data.Model.Recipe
import com.Proyecto.coffeepalace.Data.Remote.dto.IngredientDto
import com.Proyecto.coffeepalace.Data.Remote.dto.ProductDto
import com.Proyecto.coffeepalace.Data.Remote.dto.RecipeDto

fun ProductDto.toDomain(): Product = Product(
    id = this.id,
    nombre = this.nombre,
    descripcion = this.descripcion,
    imagen = this.imagen,
    precio = this.precio,
    categoria = this.categoria
)

fun RecipeDto.toDomain(): Recipe = Recipe(
    id = this.id,
    nombre = this.nombre,
    descripcion = this.descripcion,
    instrucciones = this.instrucciones,
    imagen = this.imagen
)

fun IngredientDto.toDomain(): Ingredient = Ingredient(
    id = this.id,
    nombre = this.nombre
)
