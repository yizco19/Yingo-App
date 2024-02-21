package com.zy.proyecto_final.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zy.proyecto_final.pojo.Car

@Dao
interface CarDao {
        @Insert
        fun insert(item: Car):Long
        @Query("SELECT * FROM car")
        fun getAll(): List<Car>
        @Query("SELECT * FROM car WHERE user_id = :user_id")
        fun getAll(user_id:Long?): List<Car>
        @Delete
        fun delete(item:  Car)

        @Update
        fun update(item:  Car)
        @Query("DELETE FROM car")
        fun deleteAll()
}