package com.zy.proyecto_final.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name = "email")
    var email: String? = "",
    @ColumnInfo(name = "username")
    var username: String? = "",
    @ColumnInfo(name = "password")
    var password: String? = "",
    var userPic: String? = null,
    var address: String? = "",
    var mobile: String? = "",
    var payment: String? = "",
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
)