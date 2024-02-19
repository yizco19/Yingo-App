package com.zy.proyecto_final.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category (
    var name: String="",
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
)
