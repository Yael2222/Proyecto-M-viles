package com.Proyecto.coffeepalace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.Proyecto.coffeepalace.ui.Screens.Seller.ViewStock.StockyUpdateModal
import com.Proyecto.coffeepalace.ui.Screens.Seller.ViewStock.ViewStockScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.ViewStock.ViewStockViewModel
import com.Proyecto.coffeepalace.ui.Screens.Seller.ViewUser.UserDetailsModal
import com.Proyecto.coffeepalace.ui.Screens.Seller.ViewUser.ViewUsersScreen
import com.Proyecto.coffeepalace.ui.Screens.Seller.ViewUser.ViewUsersViewModel
import com.Proyecto.coffeepalace.ui.theme.CoffeePalaceTheme

class MainActivity : ComponentActivity() {
    private val viewStockViewModel: ViewStockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CoffeePalaceTheme {
                // Estado local para controlar el diálogo
                var showDialog by remember { mutableStateOf(false) }

                ViewStockScreen(
                    viewModel = viewStockViewModel,
                    onOpenInventory = { name, currentQty ->
                        viewStockViewModel.selectedItem.value =
                            viewStockViewModel.stockItems.find { it.name == name }
                        showDialog = true
                    }
                )

                if (showDialog) {
                    viewStockViewModel.selectedItem.value?.let { item ->
                        StockyUpdateModal(
                            productName = item.name,
                            currentStock = item.quantity,
                            onSave = { newQty ->
                                viewStockViewModel.updateStock(item.name, newQty)
                                showDialog = false
                            },
                            onDismiss = { showDialog = false }
                        )
                    }
                }
            }
        }
    }
}

