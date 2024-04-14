package com.zy.proyecto_final.retrofit.entities;

import com.google.gson.annotations.SerializedName;

data class CategoryData (
    @SerializedName("categoryName")     var name: String = "",
    @SerializedName("categoryAlias")    var alias: String = "",
    @SerializedName("id")               var id: Int? = null

)
