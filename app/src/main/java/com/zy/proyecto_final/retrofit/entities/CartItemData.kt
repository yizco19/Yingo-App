package com.zy.proyecto_final.retrofit.entities

import com.google.gson.annotations.SerializedName

data class CartItemData(
    @SerializedName("cartId")
    val cartId: Int = 0,
    @SerializedName("productId")
    val productId: Int = 0,
    @SerializedName("quantity")
    val quantity: Int = 0
)
