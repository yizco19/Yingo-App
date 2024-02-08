package com.zy.proyecto_final.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @ColumnInfo(name = "email")
    var email: String? = "",
    @ColumnInfo(name = "password")
    var password: String? = "",
    @ColumnInfo(name = "name")
    var name: String? = "",
    @PrimaryKey(autoGenerate = true)
    var id:Long?=null
)