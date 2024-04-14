package com.zy.proyecto_final.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "category")
data class Category(
    var name: String = "",
    var alias: String = "",
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)