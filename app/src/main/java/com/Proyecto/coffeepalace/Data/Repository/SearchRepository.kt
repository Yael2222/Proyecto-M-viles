package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Model.Ingredient
import com.Proyecto.coffeepalace.Data.Model.Product
import com.Proyecto.coffeepalace.Data.Model.Recipe
import com.Proyecto.coffeepalace.Data.Model.SearchResult

interface SearchRepository {
    suspend fun searchProducts(query: String): List<Product>
    suspend fun searchRecipes(query: String): List<Recipe>
    suspend fun searchByIngredient(ingredient: String): List<SearchResult>
    suspend fun getAllIngredients(): List<Ingredient>
}
