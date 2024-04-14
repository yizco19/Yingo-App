package com.zy.proyecto_final.retrofit

import android.content.Context.MODE_PRIVATE

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class TokenManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("TOKEN", token)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("TOKEN", null)
    }
}
