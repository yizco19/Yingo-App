package com.zy.proyecto_final.repository

import android.content.Context
import com.zy.proyecto_final.AppDatabase
import com.zy.proyecto_final.dao.CarDao
import com.zy.proyecto_final.pojo.Car

class CarRepository (contexto: Context) {
    private val _cardao: CarDao
    init {
        val database = AppDatabase.getInstance(contexto)
        _cardao = database.carDao()
    }
    fun add(car: Car) {
        if(car.id == null){
            car.id = _cardao.insert(car)
        }
    }
    fun update(car: Car) {
        _cardao.update(car)
    }
    fun delete(car: Car) {
        _cardao.delete(car)
    }
    fun getAll(): MutableList<Car> {
        return _cardao.getAll().toMutableList()
    }
    fun getAll(user_id:Long?): MutableList<Car> {
        return _cardao.getAll(user_id).toMutableList()
    }
}