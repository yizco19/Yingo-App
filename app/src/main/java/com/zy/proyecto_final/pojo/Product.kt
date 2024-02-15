package com.zy.proyecto_final.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var name: String? = null,
    var category: String? = null

)