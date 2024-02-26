package com.zy.proyecto_final.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.zy.proyecto_final.pojo.Favorite
@Dao
interface FavoriteDao {
    @Insert
    fun insert(item: Favorite):Long

    @Query("SELECT * FROM favorite")
    fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorite WHERE user_id = :user_id")
    fun getAll(user_id:Long?): List<Favorite>

    @Delete
    fun delete(item:  Favorite)

    @Update
    fun update(item:  Favorite)

    @Query("DELETE FROM favorite")
    fun deleteAll()
}