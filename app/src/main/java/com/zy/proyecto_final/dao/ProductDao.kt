package com.zy.proyecto_final.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.zy.proyecto_final.pojo.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

    @Query("SELECT * FROM product WHERE id = :productId")
    fun getProductById(productId: Int): Product?

    /*@Insert()
    fun add(product: Product):Int

    @Delete
    fun delete(product: Product)

    @Update
    fun update(product: Product)*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(productList: List<Product>)

    @Transaction
    fun setAll(productList: List<Product>) {
        deleteAll() // Elimina todos los datos existentes en la tabla
        insertAll(productList) // Inserta los nuevos datos
    }

    @Query("DELETE FROM product")
    fun deleteAll()
}
