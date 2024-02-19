package com.zy.proyecto_final.pojo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = Category::class,
        childColumns = ["categoryid"],
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class Product(
    var name: String="",
    var description: String="",
    var categoryid: Long?=null,
    var price: Double = 0.0,
    var imageUrl: String ="",
    @PrimaryKey(autoGenerate = true)
    var id: Long ?=null
)
