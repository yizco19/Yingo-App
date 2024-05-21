package com.zy.proyecto_final.retrofit.entities

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("userPic")
    val userPic: String?,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("birthdate")
    val birthDate: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("wallet")
    val wallet: Double,
    @SerializedName("phone")
    val phone: String
)
