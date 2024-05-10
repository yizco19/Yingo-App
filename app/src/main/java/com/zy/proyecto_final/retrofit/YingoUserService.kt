package com.zy.proyecto_final.retrofit


import android.content.Context

class YingoUserService(private val context: Context) {
    companion object {
        // Utiliza la instancia de RetrofitYingo para acceder al objeto Retrofit y crear la API service
        fun getApiService(context: Context): YingoUserAPI {
            val retrofitYingo = RetrofitYingo(context)
            return retrofitYingo.retrofit.create(YingoUserAPI::class.java)
        }
    }
}