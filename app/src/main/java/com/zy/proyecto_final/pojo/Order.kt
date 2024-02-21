package com.zy.proyecto_final.pojo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zy.proyecto_final.enum.OrderStatus
import java.util.Date

@Entity(tableName = "orders")
data class rder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val product: Product,
    val quantity: Int,
    val totalPrice: Double,
    val carId: Int,
)
