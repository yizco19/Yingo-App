package com.zy.proyecto_final.viewmodel

import com.zy.proyecto_final.pojo.Favorite
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.relation.CategoryWithProducts
import com.zy.proyecto_final.repository.FavoriteRepository
import com.zy.proyecto_final.repository.ProductRepository

class FavoriteViewModel : ViewModel() {
    private lateinit var _context: Context

    //items
    lateinit var itemsrepository: FavoriteRepository
    private lateinit var _items: MutableLiveData<MutableList<Favorite>>;

    var selectedfavorite = Favorite();
    public val items: LiveData<MutableList<Favorite>>
        get() = _items

    fun init(context: Context) {
        this._context = context
        _items = MutableLiveData()
        this.itemsrepository = FavoriteRepository(context)
        this._items.value = this.itemsrepository.getAll()

    }

    fun delete(item: Favorite) {
        this._items.value?.remove(item)
        this.itemsrepository.delete(item)
        this._items.value = this.itemsrepository.getAll()
        this.update()

    }

    fun add() {
        itemsrepository.add(selectedfavorite)
        this._items.value = this.itemsrepository.getAll()

    }

    private fun update() {
        var values = this._items.value
        this._items.value = values
    }


}