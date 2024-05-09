package com.zy.proyecto_final.retrofit.entities

import com.google.gson.annotations.SerializedName

data class PaymentData(
    @SerializedName("type")     var type: Int = 0,
    @SerializedName("amout")    var amout: Double = 0.0,
    @SerializedName("phone")    var phone: String = "",
    @SerializedName("name")    var email: String = "",
    @SerializedName("address")  var address: String = ""
    )
