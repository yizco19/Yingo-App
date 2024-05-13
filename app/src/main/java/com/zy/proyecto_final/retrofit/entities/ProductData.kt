package com.zy.proyecto_final.retrofit.entities

import com.google.gson.annotations.SerializedName

data class ProductData(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("categoryId")
    val categoryId: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("productPic")
    val productPic: String = "",

    @SerializedName("stock")
    val stock: Int = 0,

    @SerializedName("price")
    val price: Double = 0.0,

    @SerializedName("visible")
    val visible: Boolean = false
)