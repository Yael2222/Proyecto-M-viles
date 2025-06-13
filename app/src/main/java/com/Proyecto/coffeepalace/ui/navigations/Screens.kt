package com.Proyecto.coffeepalace.navigation

sealed class Screens(val route: String) {
    // Pantalla de inicio de la aplicación
    object AdminDashboard : Screens("admin_dashboard_route")

    // Pantalla de gestión de categorías
    object Categories : Screens("categories_route")

    // Si tuvieras una pantalla de Login separada (ej. para Logout del Dashboard)
    object Login : Screens("login_route")
}