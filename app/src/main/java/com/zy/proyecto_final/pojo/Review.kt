package com.zy.proyecto_final.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: Int,
    val userId: Int,
    val rating: Int,
    val comment: String,
    val date: Date
)
