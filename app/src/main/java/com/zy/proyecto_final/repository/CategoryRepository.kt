package com.zy.proyecto_final.repository

import android.content.Context
import com.zy.proyecto_final.AppDatabase
import com.zy.proyecto_final.dao.CategoryDao
import com.zy.proyecto_final.pojo.Category

class CategoryRepository(context: Context) {

    private val _categoryDao: CategoryDao

    init {
        val database = AppDatabase.getInstance(context)
        _categoryDao = database.categoryDao()
    }

    fun getAll(): MutableList<Category> {
        return _categoryDao.getAll().toMutableList()
    }

    fun add(category: Category) {
        // Puedes ejecutar esta operación en un hilo de fondo si es necesario
        _categoryDao.insert(category)
    }

    fun update(category: Category) {
        // Puedes ejecutar esta operación en un hilo de fondo si es necesario
        _categoryDao.update(category)
    }

    fun delete(category: Category) {
        // Puedes ejecutar esta operación en un hilo de fondo si es necesario
        _categoryDao.delete(category)
    }
}
