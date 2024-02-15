package com.zy.proyecto_final.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.zy.proyecto_final.pojo.Product

interface ProductDao {
    @Insert
    fun insertAll(vararg products: Product)
    @Insert
    fun add(product: Product)
    @Delete
    fun delete(product: Product)
    @Update
    fun update(product: Product)
}