package com.zy.proyecto_final.retrofit

class TimeService {
    companion object {
        val apiService: TimeAPI by lazy {
            RetrofitClient.retrofit.create(TimeAPI::class.java)
        }
    }
}