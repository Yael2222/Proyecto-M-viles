package com.Proyecto.coffeepalace.navigation

sealed class Screens(val route: String) {
    // Pantalla de inicio de la aplicación
    object AdminDashboard : Screens("admin_dashboard_route")

    // Pantalla de gestión de productos
    object Products : Screens("products_route")

    // Pantalla de gestión de usuarios
    object Users : Screens("users_route")

    // Pantalla de gestión de pedidos
    object Orders : Screens("orders_route")

    // Pantalla de gestión de comentarios y calificaciones
    object CommentsRatings : Screens("comments_ratings_route")

    // Pantalla de gestión de anuncios
    object Ads : Screens("ads_route")

    // Pantalla de gestión deDenominaciones
    object ComplaintsSuggestions : Screens("complaints_suggestions_route")

    // Pantalla de gestión de categorías
    object Categories : Screens("categories_route")

    // Si tuvieras una pantalla de Login separada (ej. para Logout del Dashboard)
    object Login : Screens("login_route")
}