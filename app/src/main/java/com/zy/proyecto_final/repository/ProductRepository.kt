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

    /*fun delete(item: Product) {
        _productDao.delete(item)
    }

    fun add(item: Product): Int {
        return _productDao.add(item)
    }
    fun update(item: Product) {
        _productDao.update(item)
    }*/

    fun getAll(): MutableList<Product> {
        return _productDao.getAll().toMutableList()
    }



    fun getProductbyId(id: Int): Product? {
        return _productDao.getProductById(id)

    }

    fun insertAll(productList: List<Product>) {
        _productDao.insertAll(productList)
    }
    fun setAll(productList: List<Product>) {
        _productDao.setAll(productList)
    }

    fun deleteAll() {
        _productDao.deleteAll()
    }
}