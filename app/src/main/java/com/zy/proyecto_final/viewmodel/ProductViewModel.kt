package com.zy.proyecto_final.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.relation.CategoryWithProducts
import com.zy.proyecto_final.repository.ProductRepository

class ProductViewModel : ViewModel() {
    private lateinit var _context: Context

    lateinit var itemsrepository: ProductRepository
    private lateinit var _items: MutableLiveData<MutableList<Product>>;
    private lateinit var _category_selected: CategoryWithProducts

    var selectedproduct = Product();

    public val items: LiveData<MutableList<Product>>
        get() = _items
    var category_selected: CategoryWithProducts
        set(value) {
            this._category_selected = value;
            this._items.value = value.products.toMutableList()
        }
        get() = this._category_selected

    fun init(c: Context) {
        this._context = c
        _items = MutableLiveData()
        this.itemsrepository = ProductRepository(c)
        this._items.value = this.itemsrepository.getAll()

    }
    fun getProductbyId(id:Long):Product?{
        return this.itemsrepository.getProductbyId(id)
    }
    fun add( item:Product) {
        itemsrepository.add(item)
    }
    fun delete(item: Product) {
        this.itemsrepository.delete(item)
    }
    private fun update(item: Product) {
        this.itemsrepository.update(item)
    }

    fun insertAll(productList: List<Product>) {
        this.itemsrepository.insertAll(productList)
        this._items.value = this.itemsrepository.getAll()
    }

}