package com.zy.proyecto_final.retrofit.entities

import com.google.gson.annotations.SerializedName

data class OrderItem(
    @SerializedName("orderId") val orderId: Int? = null,
    @SerializedName("productId") val productId: Int? = null,
    @SerializedName("quantity") val quantity: Int? = null,
    @SerializedName("price") val price: Double? = null

)