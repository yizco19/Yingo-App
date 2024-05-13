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
    fun delete(product: Product)*/

    @Update
    fun update(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(productList: List<Product>)

    @Transaction
    fun setAll(productList: List<Product>) {
        val existingProducts = getAll() // Obtener todos los productos existentes
        val productsToAdd = mutableListOf<Product>()

        for (product in productList) {
            val existingProduct = existingProducts.find { it.id == product.id }
            if (existingProduct == null) {
                // El producto no existe, agregarlo a la lista de productos a insertar
                productsToAdd.add(product)
            } else {
                // El producto ya existe, verificar si hay cambios en algún atributo
                if (existingProduct.name != product.name || existingProduct.price != product.price || existingProduct.stock != product.stock) {
                    // Actualizar los valores del producto
                    existingProduct.name = product.name
                    existingProduct.price = product.price
                    existingProduct.stock = product.stock
                    update(existingProduct)
                }
            }
        }

        // Insertar los nuevos productos
        if (productsToAdd.isNotEmpty()) {
            insertAll(productsToAdd)
        }
    }
    @Query("DELETE FROM product")
    fun deleteAll()
}
