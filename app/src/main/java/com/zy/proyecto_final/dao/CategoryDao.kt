package com.zy.proyecto_final.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.User

@Dao
interface CategoryDao {
    @Insert()
    fun insert(item: Category):Long
    @Delete
    fun delete(item:  Category)
    @Update
    fun update(item:  Category)
    @Query("SELECT * FROM category")
    fun getAll(): List<Category>
}