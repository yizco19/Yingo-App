package com.zy.proyecto_final.repository

import android.content.Context
import com.zy.proyecto_final.AppDatabase
import com.zy.proyecto_final.dao.UserDao
import com.zy.proyecto_final.pojo.User

class UserRepository (var context: Context){
    private  var _userDAO: UserDao

    init{
        val database= AppDatabase.getInstance(context)
        _userDAO=database.userDao()

    }
    fun getAll(): MutableList<User>? {
        return _userDAO.getAll().toMutableList()
    }

    fun add(user: User) :Long? {
        if(user.username?.let { _userDAO.getUser(it) } ==null){
            _userDAO.add(user)
            return user.id
        }
        return null

    }

    fun getUser(usernameoremail: String): User? {
        return _userDAO.getUser(usernameoremail)

    }

    fun login(usernameoremail: String, password: String): Any {
        return _userDAO.getUser(usernameoremail, password)

    }
    fun getUserById(id:Long):User?{
        return _userDAO.getUserById(id)
    }

    fun updateUser(mD5: String, userId: Long) {
        _userDAO.updateUser(mD5, userId)
    }

    fun update(user: User) {
        _userDAO.update(user)

    }

}