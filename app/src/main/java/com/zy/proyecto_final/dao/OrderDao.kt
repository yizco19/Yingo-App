package com.zy.proyecto_final.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zy.proyecto_final.pojo.Order

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders")
    suspend fun getAllOrders(): List<Order>

    @Query(value = "SELECT * FROM orders WHERE id = :orderId")
    suspend fun getOrderById(orderId: Int): Order?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)
}
