package com.Proyecto.coffeepalace.Data.Repository

import com.Proyecto.coffeepalace.Data.Model.Ingredient
import com.Proyecto.coffeepalace.Data.Model.Recipe
import com.Proyecto.coffeepalace.Data.Model.SearchResult
import com.Proyecto.coffeepalace.Data.Remote.SupabaseClient
import com.Proyecto.coffeepalace.Data.Remote.dto.IngredientDto
import com.Proyecto.coffeepalace.Data.Remote.dto.ProductDto
import com.Proyecto.coffeepalace.Data.Remote.dto.RecipeDto
import com.Proyecto.coffeepalace.Data.Model.Product
import com.Proyecto.coffeepalace.Data.mapper.toDomain
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

/*class SearchRepositoryImpl : SearchRepository {
    private val client = SupabaseClient.client

    override suspend fun searchProducts(query: String): List<Product> {
        return try {
            val response = client.from("producto")
                .select(columns = Columns.ALL)
                .filter {
                    Product::nombre ilike "%$query%"
                }
                .decodeList<ProductDto>()

            response.map { productDto: ProductDto -> productDto.toDomain() }
        } catch (e: Exception) {
            println("Error searching products: ${e.message}")
            // Datos de prueba para testing
            getMockProducts().filter { product ->
                product.nombre.contains(query, ignoreCase = true)
            }
        }
    }

    override suspend fun searchRecipes(query: String): List<Recipe> {
        return try {
            val response = client.from("receta")
                .select(columns = Columns.ALL)
                .filter {
                    Recipe::nombre ilike "%$query%"
                }
                .decodeList<RecipeDto>()

            response.map { recipeDto: RecipeDto -> recipeDto.toDomain() }
        } catch (e: Exception) {
            println("Error searching recipes: ${e.message}")
            // Datos de prueba para testing
            getMockRecipes().filter { recipe ->
                recipe.nombre.contains(query, ignoreCase = true)
            }
        }
    }

    override suspend fun searchByIngredient(ingredient: String): List<SearchResult> {
        return try {
            // Simulación de búsqueda por ingrediente
            val products: List<Product> = searchProducts(ingredient)
            val recipes: List<Recipe> = searchRecipes(ingredient)

            val productResults: List<SearchResult> = products.map { product ->
                SearchResult.ProductResult(product)
            }
            val recipeResults: List<SearchResult> = recipes.map { recipe ->
                SearchResult.RecipeResult(recipe)
            }

            productResults + recipeResults
        } catch (e: Exception) {
            println("Error searching by ingredient: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getAllIngredients(): List<Ingredient> {
        return try {
            val response = client.from("ingrediente")
                .select(columns = Columns.ALL)
                .limit(20)
                .decodeList<IngredientDto>()

            response.map { ingredientDto: IngredientDto -> ingredientDto.toDomain() }
        } catch (e: Exception) {
            println("Error getting ingredients: ${e.message}")
            // Datos de prueba para testing
            getMockIngredients()
        }
    }

    private fun getMockProducts(): List<Product> {
        return listOf(
            Product(
                id = 1,
                nombre = "Deviled Eggs",
                descripcion = "Delicious deviled eggs with paprika",
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
                descripcion = "Rich chocolate cake with frosting",
                imagen = "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400",
                precio = 12.99,
                categoria = 3
            ),
            Product(
                id = 4,
                nombre = "Egg Sandwich",
                descripcion = "Fresh egg sandwich with herbs",
                imagen = "https://images.unsplash.com/photo-1482049016688-2d3e1b311543?w=400",
                precio = 7.99,
                categoria = 1
            )
        )
    }

    private fun getMockRecipes(): List<Recipe> {
        return listOf(
            Recipe(
                id = 1,
                nombre = "Classic Deviled Eggs",
                descripcion = "Traditional deviled eggs recipe",
                instrucciones = "1. Boil eggs for 10 minutes\n2. Cool in ice water\n3. Peel and cut in half\n4. Mix yolks with mayo and mustard\n5. Fill egg whites",
                imagen = "https://static01.nyt.com/images/2021/10/15/dining/aw-classic-deviled-eggs/aw-classic-deviled-eggs-mediumSquareAt3X.jpg"
            ),
            Recipe(
                id = 2,
                nombre = "Perfect Latte",
                descripcion = "How to make the perfect latte at home",
                instrucciones = "1. Brew strong espresso\n2. Steam milk to 150°F\n3. Pour steamed milk into espresso\n4. Create latte art",
                imagen = "https://images.unsplash.com/photo-1541167760496-1628856ab772?w=400"
            ),
            Recipe(
                id = 3,
                nombre = "Chocolate Cake Recipe",
                descripcion = "Moist and delicious chocolate cake",
                instrucciones = "1. Mix dry ingredients\n2. Add wet ingredients\n3. Bake at 350°F for 30 minutes\n4. Cool and frost",
                imagen = "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400"
            ),
            Recipe(
                id = 4,
                nombre = "Egg Benedict Recipe",
                descripcion = "Classic eggs benedict with hollandaise",
                instrucciones = "1. Toast english muffins\n2. Poach eggs\n3. Make hollandaise sauce\n4. Assemble and serve",
                imagen = "https://images.unsplash.com/photo-1482049016688-2d3e1b311543?w=400"
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
*/