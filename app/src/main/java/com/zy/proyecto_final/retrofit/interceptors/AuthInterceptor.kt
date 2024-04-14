package com.zy.proyecto_final.retrofit.interceptors

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Read the token from the JSON file
        val tokenFile = File(context.filesDir, "token.json")
        var token = ""
        try {
            FileInputStream(tokenFile).use { stream ->
                token = stream.bufferedReader().use { it.readText() }
            }
        } catch (e: IOException) {
            Log.e("AuthInterceptor", "Error reading token from JSON file: ${e.message}")
        }

        val requestWithToken = request.newBuilder()
            .header("Authorization", token)
            .build()

        return chain.proceed(requestWithToken)
    }
}