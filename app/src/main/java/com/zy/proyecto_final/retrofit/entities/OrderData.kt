package com.zy.proyecto_final.retrofit.entities

import com.google.gson.annotations.SerializedName
import java.util.Date

data class OrderData(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("userId") val userId: Int? = null,
    @SerializedName("phone") val phone: String? = "",
    @SerializedName("address") val address: String? = "",
    @SerializedName("payType") val payType: Int? = null,
    @SerializedName("createdAt") val createdAt: Date? = null,
    @SerializedName("total") val total: Double? = null,
    @SerializedName("status") val status: Int? = null,
    @SerializedName("orderItems") val orderItems: List<OrderItem>? = null
)
