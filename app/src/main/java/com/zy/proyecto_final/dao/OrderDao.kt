package com.zy.proyecto_final.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zy.proyecto_final.pojo.Order

@Dao
interface OrderDao {
    @Insert
    fun insert(item: Order): Long

    @Query("SELECT * FROM `order`")
    fun getAll(): List<Order>

    @Query("SELECT * FROM `order` WHERE user_id = :user_id")
    fun getAll(user_id: Long?): List<Order>

    @Delete
    fun delete(item: Order)

    @Update
    fun update(item: Order)
}
