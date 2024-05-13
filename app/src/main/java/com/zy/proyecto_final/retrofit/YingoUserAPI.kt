package com.zy.proyecto_final.retrofit

import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.pojo.User
import com.zy.proyecto_final.retrofit.entities.CartItemData
import com.zy.proyecto_final.retrofit.entities.CategoryData
import com.zy.proyecto_final.retrofit.entities.PaymentData
import com.zy.proyecto_final.retrofit.entities.ProductData
import com.zy.proyecto_final.retrofit.entities.Result
import com.zy.proyecto_final.retrofit.entities.UserData
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Objects

interface YingoUserAPI {

    @POST("register")
    suspend fun register(@Body registerData: RegistrationData?): Response<Result<Objects>>

    @POST("login")
    suspend fun login(
        @Query("usernameOrEmail") usernameOrEmail: String,
        @Query("password") password: String
    ): Response<Result<String>>





}