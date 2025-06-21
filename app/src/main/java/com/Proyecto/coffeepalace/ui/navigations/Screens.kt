package com.Proyecto.coffeepalace.ui.navigations

sealed class Screens(val route: String) {
    object HomeSeller : Screens("home_seller")
    object AddProduct : Screens("add_product")
    object Category : Screens("category")
    object Ingrediente : Screens("ingrediente")
    object AddReceta : Screens("add_receta")
    object DeleteProduct : Screens("delete_product")
    object ViewUsers : Screens("view_users")
    object DeleteReceta : Screens("delete_receta")
    object ViewStock : Screens("view_stock")
}

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
    object ProductDetail : Screen("product_detail")
    object Search : Screen("search")
    object RecipeDetail : Screen("recipe_detail/{recipeId}")
}
