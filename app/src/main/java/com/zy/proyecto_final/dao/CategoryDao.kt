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
    fun delete(item:  Category)*/
    @Update
    fun update(item:  Category)
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
        val existingCategories = getAll() // Obtener todas las categorías existentes
        val categoriesToAdd = mutableListOf<Category>()

        for (category in categories) {
            val existingCategory = existingCategories.find { it.id == category.id }
            if (existingCategory == null) {
                // La categoría no existe, agregarla a la lista de categorías a insertar
                categoriesToAdd.add(category)
            } else {
                // La categoría ya existe, verificar si hay cambios en nombre o alias
                if (existingCategory.name != category.name || existingCategory.alias != category.alias) {
                    // Actualizar los valores de nombre y alias
                    existingCategory.name = category.name
                    existingCategory.alias = category.alias
                    update(existingCategory)
                }
            }
        }

        // Insertar las nuevas categorías
        if (categoriesToAdd.isNotEmpty()) {
            insertAll(categoriesToAdd)
        }
    }

}