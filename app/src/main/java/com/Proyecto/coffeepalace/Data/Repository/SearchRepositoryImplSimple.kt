package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Model.Ingredient
import com.Proyecto.coffeepalace.Data.Model.Product
import com.Proyecto.coffeepalace.Data.Model.Recipe
import com.Proyecto.coffeepalace.Data.Model.SearchResult
import kotlinx.coroutines.delay

class SearchRepositoryImplSimple : SearchRepository {

    override suspend fun searchProducts(query: String): List<Product> {
        // Simular delay de red
        delay(500)

        return getMockProducts().filter { product ->
            product.nombre.contains(query, ignoreCase = true) ||
                    product.descripcion.contains(query, ignoreCase = true)
        }
    }

    override suspend fun searchRecipes(query: String): List<Recipe> {
        // Simular delay de red
        delay(500)

        return getMockRecipes().filter { recipe ->
            recipe.nombre.contains(query, ignoreCase = true) ||
                    recipe.descripcion.contains(query, ignoreCase = true)
        }
    }

    override suspend fun searchByIngredient(ingredient: String): List<SearchResult> {
        // Simular delay de red
        delay(500)

        val products: List<Product> = getMockProducts().filter { product ->
            product.descripcion.contains(ingredient, ignoreCase = true)
        }

        val recipes: List<Recipe> = getMockRecipes().filter { recipe ->
            recipe.instrucciones.contains(ingredient, ignoreCase = true) ||
                    recipe.descripcion.contains(ingredient, ignoreCase = true)
        }

        val productResults: List<SearchResult> = products.map { product ->
            SearchResult.ProductResult(product)
        }
        val recipeResults: List<SearchResult> = recipes.map { recipe ->
            SearchResult.RecipeResult(recipe)
        }

        return productResults + recipeResults
    }

    override suspend fun getAllIngredients(): List<Ingredient> {
        // Simular delay de red
        delay(300)

        return getMockIngredients()
    }

    private fun getMockProducts(): List<Product> {
        return listOf(
            Product(
                id = 1,
                nombre = "Deviled Eggs",
                descripcion = "Delicious deviled eggs with paprika and egg",
                imagen = "https://static01.nyt.com/images/2021/10/15/dining/aw-classic-deviled-eggs/aw-classic-deviled-eggs-mediumSquareAt3X.jpg",
                precio = 4.99,
                categoria = 1
            ),
            Product(
                id = 2,
                nombre = "Coffee Latte",
                descripcion = "Rich coffee with steamed milk",
                imagen = "https://images.unsplash.com/photo-1541167760496-1628856ab772?w=400",
                precio = 5.50,
                categoria = 2
            ),
            Product(
                id = 3,
                nombre = "Chocolate Cake",
                descripcion = "Rich chocolate cake with frosting and flour",
                imagen = "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400",
                precio = 12.99,
                categoria = 3
            ),
            Product(
                id = 4,
                nombre = "Egg Sandwich",
                descripcion = "Fresh egg sandwich with herbs and mayonesa",
                imagen = "https://images.unsplash.com/photo-1482049016688-2d3e1b311543?w=400",
                precio = 7.99,
                categoria = 1
            ),
            Product(
                id = 5,
                nombre = "Butter Cookies",
                descripcion = "Sweet cookies made with mantequilla and sugar",
                imagen = "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=400",
                precio = 3.99,
                categoria = 3
            )
        )
    }

    private fun getMockRecipes(): List<Recipe> {
        return listOf(
            Recipe(
                id = 1,
                nombre = "Classic Deviled Eggs",
                descripcion = "Traditional deviled eggs recipe",
                instrucciones = "1. Boil huevo for 10 minutes\n2. Cool in ice water\n3. Peel and cut in half\n4. Mix yolks with mayonesa and mostaza\n5. Fill egg whites",
                imagen = "https://static01.nyt.com/images/2021/10/15/dining/aw-classic-deviled-eggs/aw-classic-deviled-eggs-mediumSquareAt3X.jpg"
            ),
            Recipe(
                id = 2,
                nombre = "Perfect Latte",
                descripcion = "How to make the perfect latte at home",
                instrucciones = "1. Brew strong café\n2. Steam leche to 150°F\n3. Pour steamed milk into espresso\n4. Create latte art",
                imagen = "https://images.unsplash.com/photo-1541167760496-1628856ab772?w=400"
            ),
            Recipe(
                id = 3,
                nombre = "Chocolate Cake Recipe",
                descripcion = "Moist and delicious chocolate cake",
                instrucciones = "1. Mix harina and chocolate\n2. Add wet ingredients with mantequilla\n3. Bake at 350°F for 30 minutes\n4. Cool and frost with azúcar",
                imagen = "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400"
            ),
            Recipe(
                id = 4,
                nombre = "Egg Benedict Recipe",
                descripcion = "Classic eggs benedict with hollandaise",
                instrucciones = "1. Toast english muffins\n2. Poach huevo\n3. Make hollandaise sauce with mantequilla\n4. Assemble and serve",
                imagen = "https://images.unsplash.com/photo-1482049016688-2d3e1b311543?w=400"
            ),
            Recipe(
                id = 5,
                nombre = "Vanilla Cookies",
                descripcion = "Simple vanilla cookies recipe",
                instrucciones = "1. Mix harina and azúcar\n2. Add mantequilla and vainilla\n3. Bake for 15 minutes\n4. Cool and serve",
                imagen = "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=400"
            )
        )
    }

    private fun getMockIngredients(): List<Ingredient> {
        return listOf(
            Ingredient(1, "Huevo"),
            Ingredient(2, "Mayonesa"),
            Ingredient(3, "Mostaza"),
            Ingredient(4, "Café"),
            Ingredient(5, "Leche"),
            Ingredient(6, "Azúcar"),
            Ingredient(7, "Harina"),
            Ingredient(8, "Mantequilla"),
            Ingredient(9, "Chocolate"),
            Ingredient(10, "Vainilla")
        )
    }
}
