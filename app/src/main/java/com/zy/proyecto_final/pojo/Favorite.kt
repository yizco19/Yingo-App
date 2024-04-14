package com.zy.proyecto_final.pojo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Favorite(
    var product_id: Int?=null,
    var user_id: Int?=null,
    var list: String = "",
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)
