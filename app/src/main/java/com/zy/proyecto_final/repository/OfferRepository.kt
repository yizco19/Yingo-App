package com.zy.proyecto_final.repository

import android.content.Context
import com.zy.proyecto_final.AppDatabase
import com.zy.proyecto_final.dao.OfferDao
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.Offer

class OfferRepository (context: Context) {
    private var _offerDao: OfferDao

    init {
        val database = AppDatabase.getInstance(context)
        _offerDao = database.offerDao()
    }
    fun getAll():  MutableList<Offer>  {
        return _offerDao.getAll().toMutableList()
    }
    fun getOfferById(id: Int): Offer? {
        return _offerDao.getOfferById(id)
    }

    fun insertAll(offers: List<Offer>) {
        _offerDao.insertAll(offers)
    }
    fun setAll(offers: List<Offer>) {
        _offerDao.setAll(offers)
    }
    fun deleteAll() {
        _offerDao.deleteAll()
    }


}