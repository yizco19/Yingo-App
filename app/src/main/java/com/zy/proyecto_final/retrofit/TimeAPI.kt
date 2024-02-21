package com.zy.proyecto_final.retrofit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TimeAPI {
    @GET("/api/Time/current/zone")
    suspend fun getTime(@Query("timeZone") timeZone: String = "Europe/Amsterdam"): Respuesta
}
