package com.zy.proyecto_final.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zy.proyecto_final.pojo.User

@Dao
interface UserDao {

    @Insert
    fun insertAll(vararg users: User)
    @Insert
    fun insert(user: User)
    @Delete
    fun delete(user: User)
    @Update
    fun update(user: User)

    @Query("SELECT * FROM user")
    fun getAll(): List<User>
}