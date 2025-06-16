package com.Proyecto.coffeepalace.ui.Screens.AdminDashboard

import androidx.lifecycle.ViewModel
import com.Proyecto.coffeepalace.Data.Model.DashboardItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.material.icons.Icons
import com.Proyecto.coffeepalace.navigation.Screens
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ExitToApp

class DashboardViewModel : ViewModel(){
    private val _dashboardItems = MutableStateFlow(emptyList<DashboardItem>())
    val dashboardItems: StateFlow<List<DashboardItem>> = _dashboardItems

    init {
        _dashboardItems.value = listOf(
            DashboardItem("stadistics", "Stadistics", Icons.Default.Analytics, Screens.Categories.route),
            DashboardItem("users_shops", "Users", Icons.Default.People, Screens.Categories.route),
            DashboardItem("purchase_orders", "Purchase orders", Icons.Default.ShoppingCart, Screens.Categories.route),
            DashboardItem("comments_ratings", "Comments and ratings", Icons.Default.Star, Screens.Categories.route),
            DashboardItem("advertisements", "Advertisements", Icons.Default.Campaign, Screens.Categories.route),
            DashboardItem("complaints_suggestions", "Complaints and suggestions", Icons.Default.BugReport, Screens.Categories.route),
            DashboardItem("categories_tags", "Categories and tags", Icons.Default.Category, Screens.Categories.route),
            //DashboardItem("categories_tags", "Categories and tags", "dashboard/categories_tags")
            DashboardItem("logout", "Log out", Icons.Default.ExitToApp, Screens.Categories.route)
        )
    }
}