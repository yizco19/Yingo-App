package com.zy.proyecto_final.retrofit

import com.google.gson.annotations.SerializedName

data class RegistrationData(
    val username: String,
    val email: String,
    val password: String,
    val role: String
)

data class LoginData(
    val usernameOrEmail: String,
    val password: String
)
