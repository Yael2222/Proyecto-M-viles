
package com.Proyecto.coffeepalace.ui.Screens.Seller.HomeSeller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Repository.AuthRepository // <--- ¡NUEVA IMPORTACIÓN!
import kotlinx.coroutines.launch

class HomeSellerViewModel(private val authRepository: AuthRepository) : ViewModel() {
    // Puedes tener aquí otros LiveData/StateFlow relacionados con el HomeSellerScreen
    // ...

    fun signOut(onSignOutSuccess: () -> Unit) {
        viewModelScope.launch {
            val success = authRepository.signOut()
            if (success) {
                // Aquí podrías limpiar cualquier estado de usuario en tu aplicación (ej. en tu AppContainer si guardas un usuario global)
                onSignOutSuccess() // Llama al callback para navegar al login
            } else {
                // Manejar el error de cierre de sesión (ej. mostrar un Toast/Snackbar)
                println("Fallo al cerrar sesión en el ViewModel.")
            }
        }
    }
}