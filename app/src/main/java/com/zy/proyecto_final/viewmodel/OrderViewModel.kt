package com.zy.proyecto_final.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zy.proyecto_final.pojo.Car
import com.zy.proyecto_final.pojo.Order
import com.zy.proyecto_final.pojo.User
import com.zy.proyecto_final.repository.OrderRepository

class OrderViewModel : ViewModel() {
    private lateinit var _context: Context

    lateinit var itemsrepository: OrderRepository
    private lateinit var _items: MutableLiveData<MutableList<Order>>;

    var selectedorder= Order()

    public val items: LiveData<MutableList<Order>>
        get() = _items
    fun init(c: Context) {
        this._context = c
        _items = MutableLiveData()
        this.itemsrepository = OrderRepository(c)
        this._items.value = this.itemsrepository.getAll()
        /*val productList = mutableListOf(
            Product("Producto 1", "Producto 1", 1),
            Product("Producto 2", "Producto 2", 1),
        )
        for (product in productList) {
            add(Order(null, 1, 1,product.name,product.price,8,product.imageUrl))
        }*/
    }

    fun add() {
        if(this.selectedorder.id == null){
            this._items.value?.add(this.selectedorder)
            itemsrepository.add(this.selectedorder)
            var t= this.selectedorder
            this.update()

        }
        itemsrepository.add(this.selectedorder)
    }
    fun getAll():MutableList<Order>{
        return this.itemsrepository.getAll(selectedorder.user_id)
    }


    fun delete(item: Order) {
        this.itemsrepository.delete(item)
    }
    fun deleteAll(){
        this.itemsrepository.deleteAll()
    }

    fun update() {
        this._items.value = this.itemsrepository.getAll()
    }

    fun setAll(user: User, value: MutableList<Car>) {
        var listOrder : MutableList<Order> = mutableListOf()
        for(item in value){
            listOrder.add(Order(null,
                user.id!!,
                item.product_id!!,
                item.product_count,
                user.address,
                user.mobile
            ))
        }
        this._items.value = listOrder
        this.itemsrepository.setAll(listOrder)
    }

    fun clearOrder() {
        deleteAll()
        _items.value = mutableListOf()
        _items.value = _items.value
    }

}