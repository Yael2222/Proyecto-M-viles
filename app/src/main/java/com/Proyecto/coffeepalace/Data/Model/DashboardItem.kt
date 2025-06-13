package com.Proyecto.coffeepalace.Data.Model

import androidx.compose.ui.graphics.vector.ImageVector

data class DashboardItem(
    val id: String,
    val title: String,
    val icon: ImageVector? = null,
    val route: String,
)