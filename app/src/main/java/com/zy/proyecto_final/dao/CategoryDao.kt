package com.zy.proyecto_final.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.User
import com.zy.proyecto_final.relation.CategoryWithProducts

@Dao
interface CategoryDao {
    /*@Insert()
    fun insert(item: Category):Int
    @Delete
    fun delete(item:  Category)
    @Update
    fun update(item:  Category)*/
    @Query("SELECT * FROM category")
    fun getAll(): List<Category>

    @Query("SELECT * FROM category WHERE id = :id")
    fun getCategoryWithProducts(id: Int): CategoryWithProducts

    @Insert
    fun insertAll(additionalCategories: List<Category>): List<Long>

    @Query("DELETE FROM category")
    fun deleteAll()

    @Transaction
    fun setAll(categories: List<Category>) {
        deleteAll() // Elimina todas las categorías existentes
        insertAll(categories) // Inserta las nuevas categorías
    }
}