package com.Proyecto.coffeepalace.ui.Screens.Client.Profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Proyecto.coffeepalace.Data.Daos.user.DaoUsuarioImpl
import com.Proyecto.coffeepalace.Data.Model.Client.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val dao = DaoUsuarioImpl()
    private val _user = MutableStateFlow<Usuario?>(null)
    val user = _user.asStateFlow()

    //variables to update user information
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _address = MutableStateFlow("")
    val address = _address.asStateFlow()

    private val _cellphone = MutableStateFlow("")
    val cellphone = _cellphone.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateAddress(newAddress: String) {
        _address.value = newAddress
    }

    fun updateCellphone(newCellphone: String) {
        _cellphone.value = newCellphone
    }

    fun getUserById(userId: Long) {
        viewModelScope.launch {
            val userData = dao.getUserById(userId)
            Log.d("ProfileViewModel", "User data: $userData")
            _user.value = userData
            _email.value = userData?.correo ?: ""
            _password.value = userData?.nombre ?: ""
//            _address.value = userData?.address ?: ""
//            _cellphone.value = userData?.cellphone ?: ""
        }
    }


    fun updateUserById(userId: Long) {
        if (_email.value.isEmpty() || _password.value.isEmpty())
            viewModelScope.launch {
                val updatedUser = Usuario(
                    id = userId,
                    correo = _email.value,
                    nombre = _password.value,
//                    address = _address.value,
//                    cellphone = _cellphone.value,
                    imagen = _user.value?.imagen ?: "",
                    rol = _user.value?.rol ?: "",
                    auth_id = _user.value?.auth_id ?: ""
                )
                _user.value = dao.updateUserById(userId, updatedUser)
            }
    }
}