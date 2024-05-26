package com.zy.proyecto_final.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zy.proyecto_final.pojo.Car
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.repository.CarRepository

class CarViewModel: ViewModel() {
    private lateinit var _context: Context

    lateinit var itemsrepository: CarRepository
    private lateinit var _items: MutableLiveData<MutableList<Car>>;

    var selectedcar= Car()

    public val items: LiveData<MutableList<Car>>
        get() = _items
    fun init(c: Context) {
        this._context = c
        _items = MutableLiveData()
        this.itemsrepository = CarRepository(c)
        this._items.value = this.itemsrepository.getAll()
        /*val productList = mutableListOf(
            Product("Producto 1", "Producto 1", 1),
            Product("Producto 2", "Producto 2", 1),
        )
        for (product in productList) {
            add(Car(null, 1, 1,product.name,product.price,8,product.imageUrl))
        }*/
    }

    fun add() {
        if (this.selectedcar.id == null) {
            val existingCar = this._items.value?.find { car: Car ->  car.product_id == this.selectedcar.product_id }
            if (existingCar != null) {
                existingCar.product_count += 1
                itemsrepository.update(existingCar) // Actualiza la cantidad del elemento existente
            } else {
                // Si no existe, agrega un nuevo elemento al carrito
                this._items.value?.add(this.selectedcar)
                itemsrepository.add(this.selectedcar)
            }

            this.updateAll()
        }
    }

    fun getAll():MutableList<Car>{
        return this.itemsrepository.getAll(selectedcar.user_id)
    }


    fun delete(item: Car) {
        this.itemsrepository.delete(item)
        this._items.value = this.itemsrepository.getAll()
    }

    fun deleteByProductId(productId: Int) {
        this.itemsrepository.deleteByProductId(productId)
        this._items.value = this.itemsrepository.getAll()
    }
    fun deleteAll() {
        this.itemsrepository.deleteAll()
    }
    fun update(item: Car) {
        this.selectedcar = item
        this.itemsrepository.update(selectedcar)
        this._items.value = this.itemsrepository.getAll()
    }


    fun updateAll() {
        val values =this._items.value
        this._items.value = this.itemsrepository.getAll()
    }

    fun clearCart() {
        deleteAll()
        _items.value = mutableListOf()
        _items.value = _items.value
    }
}