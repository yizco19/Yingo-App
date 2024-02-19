package com.zy.proyecto_final.pojo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zy.proyecto_final.enum.OrderStatus
import java.util.Date

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    @Embedded
    val products: Pair<Product,Int>,//producto y cantidad
    val totalPrice: Double,
    val orderDate: Date,
    val status: OrderStatus
)
