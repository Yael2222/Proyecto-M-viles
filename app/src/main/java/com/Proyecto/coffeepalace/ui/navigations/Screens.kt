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
    object Orders : Screens("orders_screen") // <--- ¡NUEVA LÍNEA!
    object Splash : Screens("splash")
    object Login : Screens("login")
    object SignUp : Screens("signup")
    object ForgotPassword : Screens("forgot_password")
    object ProductDetail : Screens("product_detail")
    object Search : Screens("search")
    object RecipeDetail : Screens("recipe_detail/{recipeId}")
}

