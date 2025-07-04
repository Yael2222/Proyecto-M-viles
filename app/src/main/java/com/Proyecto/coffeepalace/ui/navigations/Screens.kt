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
    object Orders : Screens("orders_screen")
    object Splash : Screens("splash_screen")
    object Login : Screens("login")
    object SignUp : Screens("signup")
    object ForgotPassword : Screens("forgot_password")
    object ProductDetail : Screens("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }    object RecetaDetail : Screens("receta_detail/{recetaId}") {
        fun createRoute(recetaId: Long) = "receta_detail/$recetaId"
    }
    object Home : Screens("home_screen") // This is the new Home page
    object ShoppingCart : Screens("shopping_cart") // NUEVO
    object Search : Screens("search")
    object User_Settings : Screens("user_settings_screen")
}
