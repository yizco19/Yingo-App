package com.zy.proyecto_final.pojo

import android.media.Image
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(foreignKeys = [
    ForeignKey(
        entity = Category::class,
        childColumns = ["categoryId"],
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE
    )
],tableName = "product")
data class Product(
    @PrimaryKey
    var id: Int?      = null,
    var categoryId: Int?      = null,
    var name: String? = null,
    var description: String? = null,
    var productPic: String? = null,
    var price: Double? = null,
    var visible: Boolean? = null
)