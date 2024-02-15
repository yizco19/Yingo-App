package com.zy.proyecto_final.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.User

@Dao
interface CategoryDao {
    @Insert
    fun insertAll(vararg categories: Category)
    @Insert
    fun add(category: Category)
    @Delete
    fun delete(category: Category)
    @Update
    fun update(category: Category)
}