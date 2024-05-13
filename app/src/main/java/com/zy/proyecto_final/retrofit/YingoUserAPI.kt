package com.zy.proyecto_final.retrofit

import android.net.Uri
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.pojo.User
import com.zy.proyecto_final.retrofit.entities.CartItemData
import com.zy.proyecto_final.retrofit.entities.CategoryData
import com.zy.proyecto_final.retrofit.entities.PaymentData
import com.zy.proyecto_final.retrofit.entities.ProductData
import com.zy.proyecto_final.retrofit.entities.Result
import com.zy.proyecto_final.retrofit.entities.UpdatePwdData
import com.zy.proyecto_final.retrofit.entities.UserData
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query
import java.io.File
import java.util.Objects

interface YingoUserAPI {
    @POST("register")
    suspend fun register(@Body registerData: RegistrationData?): Response<Result<Objects>>

    @POST("login")
    suspend fun login(
        @Query("usernameOrEmail") usernameOrEmail: String,
        @Query("password") password: String
    ): Response<Result<String>>
    @GET("user/userInfo")
    suspend fun getUser(): Response<Result<UserData>>
    @PATCH("updatePwd")
    suspend fun updatePwd(@Body updatePwdData: UpdatePwdData): Response<Result<Objects>>

    @PUT("update")
    suspend fun update(@Body userData: UserData): Response<Result<Objects>>

    @PATCH("updateAvatar")
    suspend fun uploadAvatar(@Query("avatarUrl") uri : Uri): Response<Result<Objects>>



}