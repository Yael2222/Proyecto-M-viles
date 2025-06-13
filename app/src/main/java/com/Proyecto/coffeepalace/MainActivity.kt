package com.Proyecto.coffeepalace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.Proyecto.coffeepalace.ui.theme.CoffeePalaceTheme
import com.Proyecto.coffeepalace.ui.Screens.HomePage.HomePage
import com.Proyecto.coffeepalace.ui.Screens.AdminDashboard.AdminDashboardScreen
import androidx.navigation.compose.rememberNavController
import com.Proyecto.coffeepalace.ui.navigations.MainNavigation
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.Serializable

import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.from


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoffeePalaceTheme {
                MyScreen()
            }
        }
    }
}

val supabase2 = createSupabaseClient(
    supabaseUrl = "https://gcdxyzsgpkmmiemvaskx.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdjZHh5enNncGttbWllbXZhc2t4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDgyMDgzODcsImV4cCI6MjA2Mzc4NDM4N30.ooXN38ZDnjEWd4uzaZJsoG8IzfERlX009nVqh9ggpq0" // TU CLAVE ANÓNIMA DE SUPABASE
) {

    install(Postgrest)
}
@Serializable
data class categoria (
    val id: Int,
    val nombre_categoria: String,
)

@Composable
fun CategoryListSimple() {
    val categories = remember { mutableStateListOf<categoria>() }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                val result = supabase2.from("categoria").select().decodeList<categoria>()
                categories.clear()
                categories.addAll(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    LazyColumn {
        items(categories) { category ->
            ListItem(
                headlineContent = {
                    Text(text = category.nombre_categoria)
                }
            )
        }
    }
}

@Composable
fun MyScreen() {
    CategoryListSimple()
}


