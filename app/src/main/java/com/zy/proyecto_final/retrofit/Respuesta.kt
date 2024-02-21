package com.zy.proyecto_final.retrofit

import com.google.gson.annotations.SerializedName

data class Respuesta(
    @SerializedName("year") val year: Int? = null,
    @SerializedName("month") val month: Int? = null,
    @SerializedName("day") val day: Int? = null,
    @SerializedName("hour") val hour: Int? = null,
    @SerializedName("minute") val minute: Int? = null,
    @SerializedName("seconds") val seconds: Int? = null,
    @SerializedName("milliSeconds") val milliSeconds: Int? = null,
    @SerializedName("dateTime") val dateTime: String? = null,
    @SerializedName("date") val date: String? = null,
    @SerializedName("time") val time: String? = null,
    @SerializedName("timeZone") val timeZone: String? = null,
    @SerializedName("dayOfWeek") val dayOfWeek: String? = null,
    @SerializedName("dstActive") val dstActive: Boolean = false
)