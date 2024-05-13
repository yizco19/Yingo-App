package com.zy.proyecto_final.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zy.proyecto_final.pojo.User
import com.zy.proyecto_final.repository.UserRepository

class UserViewModel : ViewModel() {
    private lateinit var _context: Context
    lateinit var itemsrepository: UserRepository
    private lateinit var _items: MutableLiveData<MutableList<User>>;
    var userlogged= User()
    public val items: LiveData<MutableList<User>>
        get() = _items

    fun init(c: Context) {
        this._context = c
        _items = MutableLiveData()
        this.itemsrepository = UserRepository(c)
        this._items.value=this.itemsrepository.getAll()
    }
    fun add(user: User) :Boolean{

        if( user.username?.let { this.itemsrepository.getUser(it) } ==null){
            this._items.value=this.itemsrepository.getAll()
            this.itemsrepository.add(user)
            var t=this.userlogged
            this.update(user)
            return true
        }
        this.itemsrepository.add(this.userlogged)
        this.userlogged=user
        return false


    }

    fun addUser(user: User) {
        this.userlogged=user
        this.itemsrepository.add(this.userlogged)

    }
    fun getUserById(id:Int):User?{
        return this.itemsrepository.getUserById(id)
    }

    fun update(user: User) {
        var values = this._items.value
        this._items.value = values
        this.itemsrepository.update(user)
    }

    fun login(usernameoremail: String, password: String): Boolean {
        val user = this.itemsrepository.getUser(usernameoremail)
        if (user != null && this.itemsrepository.login(usernameoremail, password) != null) {
            this._items.value = this.itemsrepository.getAll()
            this.userlogged = user
            return true
        }

        return false
    }

    fun logout() {
        this.userlogged = User()
        this.itemsrepository.clear()
    }

    fun updateUser(mD5: String, userId: Int) {
        this.itemsrepository.updateUser(mD5, userId)
    }


}