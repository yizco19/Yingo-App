package com.zy.proyecto_final.retrofit

import android.content.Context
import com.zy.proyecto_final.retrofit.interceptors.AuthInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitYingo(private val context: Context) {  // Inject context

    companion object {
        private const val BASE_URL = "http://192.168.100.111:8080"
    }

    val retrofit: Retrofit by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context)) // Add interceptor
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("rol", "4983a0ab83ed86e0e7213c8783940193")
                    .build()
                chain.proceed(request)
            })
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)  // Set the client with interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}