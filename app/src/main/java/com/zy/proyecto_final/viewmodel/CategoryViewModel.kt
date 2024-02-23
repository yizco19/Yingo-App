package com.zy.proyecto_final.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.relation.CategoryWithProducts
import com.zy.proyecto_final.repository.CategoryRepository

class CategoryViewModel : ViewModel() {
    private lateinit var _context: Context

    //items
    lateinit var itemsrepository: CategoryRepository
    private lateinit var _items: MutableLiveData<MutableList<Category>>;

    var selectedcategory = Category();
    public val items: LiveData<MutableList<Category>>
        get() = _items

    fun init(context: Context) {
        this._context = context
        _items = MutableLiveData()

        this.itemsrepository = CategoryRepository(context)
        /*var categorytest = Category("Movil", 1)
this.itemsrepository.add(categorytest)

var categorytestu = Category("Monitor", 2)
this.itemsrepository.add(categorytestu)*/


        this._items.value = this.itemsrepository.getAll()

    }

    fun getCategoryWithProducts(): CategoryWithProducts? {
        return this.selectedcategory.id?.let { this.itemsrepository.getCategoryWithProducts(it) }

    }
}