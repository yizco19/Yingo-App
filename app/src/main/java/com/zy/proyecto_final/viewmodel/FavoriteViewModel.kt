package com.zy.proyecto_final.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zy.proyecto_final.pojo.Favorite
import com.zy.proyecto_final.repository.FavoriteRepository

class FavoriteViewModel : ViewModel() {
    private lateinit var _context: Context

    // Items
    lateinit var itemsrepository: FavoriteRepository
    private val _items: MutableLiveData<MutableList<Favorite>> = MutableLiveData(mutableListOf())
    val items: LiveData<MutableList<Favorite>>
        get() = _items

    var selectedfavorite = Favorite()

    fun init(context: Context) {
        this._context = context
        this.itemsrepository = FavoriteRepository(context)
        this._items.value = this.itemsrepository.getAll().toMutableList()
    }

    fun delete() {
        this._items.value?.let { favorites ->
            favorites.remove(selectedfavorite)
            this.itemsrepository.delete(selectedfavorite)
            _items.postValue(favorites)  // Notify observers of the change
        }
    }

    fun add() {
        val currentFavorites = _items.value ?: mutableListOf()
        // Check if the item already exists
        if (currentFavorites.any { it.id == selectedfavorite.id }) {
            return
        }
        this.itemsrepository.add(selectedfavorite)
        currentFavorites.add(selectedfavorite)
        _items.value = currentFavorites
    }
}
