package com.zy.proyecto_final.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zy.proyecto_final.pojo.Offer
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.repository.OfferRepository

class OfferViewModel : ViewModel()  {
    private lateinit var _context: Context

    private lateinit var itemsrepository: OfferRepository
    private var _items = MutableLiveData<MutableList<Offer>>()

    var selectedOffer = Offer()

    public val items: LiveData<MutableList<Offer>>
        get() = _items


    fun init(c: Context) {
        this._context = c
        _items = MutableLiveData()
        this.itemsrepository = OfferRepository(c)
        this._items.value = this.itemsrepository.getAll()
        }

    fun insertAll(items: List<Offer>) {
        this.itemsrepository.insertAll(items)
        this._items.value = this.itemsrepository.getAll()
    }
    fun setAll(items: List<Offer>) {
        this.itemsrepository.setAll(items)
        this._items.value = this.itemsrepository.getAll()
    }

    fun deleteAll() {
        this.itemsrepository.deleteAll()
    }
    fun getOfferById(id: Int): Offer? {
        return this.itemsrepository.getOfferById(id)
    }

}