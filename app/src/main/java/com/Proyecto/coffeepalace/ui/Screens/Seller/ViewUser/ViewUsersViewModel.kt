package com.Proyecto.coffeepalace.ui.Screens.Seller.ViewUser


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class UserModel(
    val name: String,
    val email: String,
    val lastVisit: String,
    val purchases: Int
)

class ViewUsersViewModel : ViewModel() {
    var users = mutableStateListOf(
        UserModel("Ana Sánchez", "ana@example.com", "Hace 1 día", 15),
        UserModel("Carlos López", "carlos@example.com", "Hace 3 días", 9)
    )

    var selectedUser = mutableStateOf<UserModel?>(null)
    var searchQuery = mutableStateOf("")

    fun filterUsers(): List<UserModel> {
        return users.filter {
            it.name.contains(searchQuery.value, ignoreCase = true)
        }
    }

    fun selectUser(user: UserModel) {
        selectedUser.value = user
    }

    fun clearSelectedUser() {
        selectedUser.value = null
    }
}
