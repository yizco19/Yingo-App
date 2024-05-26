package com.zy.proyecto_final.pojo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity()
data class Favorite(
    var product_id: Int?=null,
    var user_id: Int?=null,
    @PrimaryKey
    val id: Int? = null
)
