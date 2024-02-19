package com.zy.proyecto_final.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.zy.proyecto_final.AppDatabase
import com.zy.proyecto_final.dao.CategoryDao
import com.zy.proyecto_final.pojo.Category

class CategoryRepository(application: Application) {

    private val _categoryDao: CategoryDao

    init {
        val database = AppDatabase.getInstance(application)
        _categoryDao = database.categoryDao()
    }

    fun getAll(): List<Category> {
        return _categoryDao.getAll()
    }

    fun insert(category: Category) {
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
