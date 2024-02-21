package com.zy.proyecto_final.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {

        private val BASE_URL = "https://timeapi.io"
        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)

                .addConverterFactory(GsonConverterFactory.create())

                .build()
        }
    }
}
