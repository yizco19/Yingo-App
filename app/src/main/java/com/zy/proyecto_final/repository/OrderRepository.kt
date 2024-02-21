package com.zy.proyecto_final.repository

import android.content.Context
import com.zy.proyecto_final.AppDatabase
import com.zy.proyecto_final.dao.OrderDao
import com.zy.proyecto_final.pojo.Order

class OrderRepository (contexto: Context) {
    private val _orderdao: OrderDao
    init {
        val database = AppDatabase.getInstance(contexto)
        _orderdao = database.orderDao()
    }
    fun add(item: Order) {
        if(item.id == null){
            item.id = _orderdao.insert(item)
        }
    }
    fun update(Order: Order) {
        _orderdao.update(Order)
    }
    fun delete(Order: Order) {
        _orderdao.delete(Order)
    }
    fun getAll(): MutableList<Order> {
        return _orderdao.getAll().toMutableList()
    }
    fun getAll(user_id:Long?): MutableList<Order> {
        return _orderdao.getAll(user_id).toMutableList()
    }
}