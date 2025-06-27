package com.Proyecto.coffeepalace.Data.Remote.auth.Login

import com.Proyecto.coffeepalace.Data.Remote.auth.UserResponse

data class LoginResponse(
    val token: String,
    val user: UserResponse
)