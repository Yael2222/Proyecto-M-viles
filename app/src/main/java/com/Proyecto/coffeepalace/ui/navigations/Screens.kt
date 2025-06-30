package com.Proyecto.coffeepalace.ui.navigations

object NavigationRoutes {
    const val LOGIN = "login"
    const val FORGOT_PASSWORD = "forgot_password"
    const val SIGN_UP = "sign_up"
    const val HOME = "home"
    const val SEARCH = "search"
    const val CATEGORY = "category"
    const val PROFILE = "profile"
    const val CART = "cart"
    const val PRODUCT_DETAIL = "product_detail/{productId}/{userId}"
    const val RECIPE_DETAIL = "recipe_detail/{recipeId}"

    fun productDetail(productId: Int, userId: Int = 1) = "product_detail/$productId/$userId"
    fun recipeDetail(recipeId: Int) = "recipe_detail/$recipeId"
}





/*sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
    object ProductDetail : Screen("product_detail")
    object Search : Screen("search")
    object RecipeDetail : Screen("recipe_detail/{recipeId}")
}*/
