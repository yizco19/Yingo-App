package com.zy.proyecto_final.pojo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            childColumns = ["product_id"],  // Replace "userid" with the correct column name
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ,ForeignKey(
        entity = User::class,
        childColumns = ["user_id"],  // Replace "userid" with the correct column name
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE
    )
    ]
)
data class Car(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var user_id: Long? = null,
    var product_id: Long? = null,
    var product_name: String = "",
    var product_price: Double = 0.0,
    var product_count: Int = 0,
    var product_image: Int? = 0
)
