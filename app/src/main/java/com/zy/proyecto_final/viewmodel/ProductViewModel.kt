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
    private lateinit var _items: MutableLiveData<MutableList<Product>>
    private var _category_selected: CategoryWithProducts? = null

    var selectedproduct = Product()

    val items: LiveData<MutableList<Product>>
        get() = _items

    var category_selected: CategoryWithProducts?
        set(value) {
            _category_selected = value
            _items.value = value?.products?.toMutableList() ?: mutableListOf()
        }
        get() = _category_selected

    fun init(c: Context) {
        _context = c
        _items = MutableLiveData()
        itemsrepository = ProductRepository(c)
        _items.value = itemsrepository.getAll()
    }

    fun filter(query: String) {
        val filteredList = mutableListOf<Product>()
        _category_selected?.products?.forEach { product ->
            if (product.name?.contains(query, ignoreCase = true) == true) {
                filteredList.add(product)
            }
        }
        _items.value = filteredList
    }

    fun getProductbyId(id: Int): Product? {
        return itemsrepository.getProductbyId(id)
    }

    // Descomentar si es necesario:
    /*fun add(item: Product) {
        itemsrepository.add(item)
    }

    fun delete(item: Product) {
        itemsrepository.delete(item)
    }

    private fun update(item: Product) {
        itemsrepository.update(item)
    }

    fun insertAll(productList: List<Product>) {
        itemsrepository.insertAll(productList)
        _items.value = itemsrepository.getAll()
    }*/

    fun setAll(productList: List<Product>) {
        itemsrepository.setAll(productList)
        _items.value = itemsrepository.getAll()
    }

    fun deleteAll() {
        itemsrepository.deleteAll()
    }

    fun getProductOfferList(i: Int): List<Product> {
        return itemsrepository.getProductOfferList(i)

    }
}
