package com.zy.proyecto_final.retrofit


import android.content.Context

class YingoService(private val context: Context) {
    companion object {
        // Utiliza la instancia de RetrofitYingo para acceder al objeto Retrofit y crear la API service
        fun getApiService(context: Context): YingoAPI {
            val retrofitYingo = RetrofitYingo(context)
            return retrofitYingo.retrofit.create(YingoAPI::class.java)
        }
    }
}