package com.zy.proyecto_final.retrofit.entities

import com.google.gson.annotations.SerializedName
import java.util.Date

data class OfferData(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
    @SerializedName("discount") var discount: Double,
    @SerializedName("startDate") var startDate: Date,
    @SerializedName("endDate") var endDate: Date
)
