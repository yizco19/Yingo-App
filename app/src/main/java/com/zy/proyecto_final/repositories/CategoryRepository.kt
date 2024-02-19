package com.zy.proyecto_final.repositories
import android.content.Context
import com.zy.proyecto_final.AppDatabase
import com.zy.proyecto_final.dao.CategoryDao
import com.zy.proyecto_final.pojo.Category

class CategoryRepository (var context: Context) {
    private var _categoryDao: CategoryDao

    init{
        val database= AppDatabase.getInstance(context)
        _categoryDao=database.categoryDao()

    }

    fun getAll():MutableList<Category>{
        return _categoryDao.getAll().toMutableList()
    }


}