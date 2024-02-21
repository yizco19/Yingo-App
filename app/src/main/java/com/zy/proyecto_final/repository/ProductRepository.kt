package com.zy.proyecto_final.repository

import android.content.Context
import com.zy.proyecto_final.AppDatabase
import com.zy.proyecto_final.dao.ProductDao
import com.zy.proyecto_final.pojo.Product

class ProductRepository(context: Context) {
    private var _productDao : ProductDao

    init {
        val database = AppDatabase.getInstance(context)
        _productDao = database.productDao()
    }

    fun delete(item: Product) {
        _productDao.delete(item)
    }

    fun add(item: Product): Long {
        return _productDao.add(item)
    }

    fun getAll(): MutableList<Product> {
        return _productDao.getAll().toMutableList()
    }

    fun update(item: Product) {
        _productDao.update(item)
    }

    fun getProductbyId(id: Long): Product? {
        return _productDao.getProductById(id)

    }
}