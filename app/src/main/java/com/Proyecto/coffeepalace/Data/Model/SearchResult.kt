package com.Proyecto.coffeepalace.Data.Model

sealed class SearchResult {
    data class ProductResult(val product: Product) : SearchResult()
    data class RecipeResult(val recipe: Recipe) : SearchResult()
}