package com.zy.proyecto_final.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zy.proyecto_final.pojo.Review

@Dao
interface ReviewDao {
    @Query("SELECT * FROM reviews WHERE productId = :productId")
    suspend fun getReviewsByProductId(productId: Int): List<Review>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: Review)

    @Delete
    suspend fun deleteReview(review: Review)
}
