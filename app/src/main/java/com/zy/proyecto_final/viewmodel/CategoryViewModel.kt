package com.zy.proyecto_final.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.repositories.CategoryRepository

class CategoryViewModel : ViewModel() {
    private lateinit var _context: Context

    //items
    lateinit var itemsrepository: CategoryRepository
    private lateinit var _items: MutableLiveData<MutableList<Category>>;

    var selectedcliente = Category();

    public val items: LiveData<MutableList<Category>>
        get() = _items

    fun init(c: Context) {
        this._context = c
        _items = MutableLiveData()
        this.itemsrepository = CategoryRepository(c)
        this._items.value = this.itemsrepository.getAll()

    }
}