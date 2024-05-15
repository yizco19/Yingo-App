package com.zy.proyecto_final.retrofit

import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.pojo.User
import com.zy.proyecto_final.retrofit.entities.CartItemData
import com.zy.proyecto_final.retrofit.entities.CategoryData
import com.zy.proyecto_final.retrofit.entities.OrderData
import com.zy.proyecto_final.retrofit.entities.PaymentData
import com.zy.proyecto_final.retrofit.entities.ProductData
import com.zy.proyecto_final.retrofit.entities.Result
import com.zy.proyecto_final.retrofit.entities.UserData
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query
import java.util.Objects

interface YingoAPI {
    @POST("register")
    suspend fun register(@Body registerData: RegistrationData?): Response<Result<Objects>>

    @POST("login")
    suspend fun login(
        @Query("usernameOrEmail") usernameOrEmail: String,
        @Query("password") password: String
    ): Response<Result<String>>

    @GET("category")
    suspend fun getCategories(): Response<Result<List<CategoryData>>>

    @GET("product/list")
    suspend fun getProducts(): Response<Result<List<ProductData>>>

    @GET("user/userInfo")
    suspend fun getUser(): Response<Result<UserData>>

    @GET("cart/items")
    suspend fun getCart(): Response<Result<List<ProductData>>>

    @POST("cart/addItems")
    suspend fun setCar(@Body carData: List<CartItemData>) : Response<Result<Objects>>

    @POST("payment")
    suspend fun processPayment(@Body paymentData: PaymentData): Response<Result<Objects>>

    @GET("order/list")
    suspend fun getOrders(@Query("status") status: Int): Response<Result<List<OrderData>>>

    @GET( "order/detail")
    suspend fun getOrderDetail(@Query("id") id: Int): Response<Result<OrderData>>

    @PUT("user/activateCode")
    suspend fun redeemCode(@Query("activateCode") redeemCode: String) : Response<Result<Objects>>


}