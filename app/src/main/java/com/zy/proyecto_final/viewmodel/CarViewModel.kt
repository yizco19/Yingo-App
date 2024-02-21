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
        if(this.selectedcar.id == null){
            this._items.value?.add(this.selectedcar)
            itemsrepository.add(this.selectedcar)
            var t= this.selectedcar
            this.update()

        }
        itemsrepository.add(this.selectedcar)
    }
    fun getAll():MutableList<Car>{
        return this.itemsrepository.getAll(selectedcar.user_id)
    }


    fun delete(item: Car) {
this.itemsrepository.delete(item)
    }

    fun update() {
        val values =this._items.value
        this._items.value = this.itemsrepository.getAll()
    }
}