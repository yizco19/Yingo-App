package com.zy.proyecto_final.repository

import android.content.Context
import com.zy.proyecto_final.AppDatabase
import com.zy.proyecto_final.dao.FavoriteDao
import com.zy.proyecto_final.pojo.Favorite

class FavoriteRepository(context: Context) {

    private val _favoriteDao: FavoriteDao

    init {
        val database = AppDatabase.getInstance(context)
        _favoriteDao = database.favoriteDao()
    }

    fun getAll(): MutableList<Favorite> {
        return _favoriteDao.getAll().toMutableList()
    }

    fun add(item: Favorite) {
        // Puedes ejecutar esta operación en un hilo de fondo si es necesario
        _favoriteDao.insert(item)
    }

    fun update(item: Favorite) {
        // Puedes ejecutar esta operación en un hilo de fondo si es necesario
        _favoriteDao.update(item)
    }

    fun delete(item: Favorite) {
        // Puedes ejecutar esta operación en un hilo de fondo si es necesario
        _favoriteDao.delete(item)
    }
}
