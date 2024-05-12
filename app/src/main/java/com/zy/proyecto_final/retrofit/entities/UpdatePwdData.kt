package com.zy.proyecto_final.retrofit.entities

import com.google.gson.annotations.SerializedName

class UpdatePwdData(
    @SerializedName("oldPwd") var  oldPwd: String? = null,
    @SerializedName("newPwd") var  newPwd: String? = null,
    @SerializedName("rePwd") var  confirmPwd: String? = null
)