package com.zy.proyecto_final.retrofit

import android.content.Context
import android.net.Uri
import android.util.Log
import com.zy.proyecto_final.pojo.Car
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.pojo.User
import com.zy.proyecto_final.retrofit.entities.CartItemData

import com.zy.proyecto_final.retrofit.entities.Result
import com.zy.proyecto_final.retrofit.entities.UpdatePwdData
import com.zy.proyecto_final.retrofit.entities.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.Response
import java.io.File
import java.util.Objects

class YingoUserRepository(c: Context) {

    private lateinit var _context: Context
    private var _serviceUser: YingoUserAPI

    init {
        _context = c
        _serviceUser = YingoUserService.getApiService(_context)

    }
    suspend fun register(registerData: RegistrationData): Response<Result<Objects>> {
        return withContext(Dispatchers.IO){
            _serviceUser.register(registerData.username, registerData.email, registerData.password)
        }

    }
    suspend fun login(loginData: LoginData?): Response<Result<String>> {
        return withContext(Dispatchers.IO) {
            _serviceUser.login(loginData?.usernameOrEmail ?: "", loginData?.password ?: "")
        }
    }

    suspend fun getUser(): User? {
        return withContext(Dispatchers.IO) {
            var user = User()
            val response = _serviceUser.getUser()

            if (response.isSuccessful) {

                response.body()?.data?.let {
                    user = adapterUser(it)
                }
            }
            return@withContext user
        }
    }


    private  fun adapterUser(data: UserData): User{
        var user = User()
        user.username = data.username
        user.nickname = data.nickname
        user.email = data.email
        user.id = data.id
        user.address = data.address
        user.mobile = data.phone
        user.userPic = data.userPic
        user.wallet = data.wallet



        return user
    }
    private fun adapterUserData(data:User): UserData{
        var user = UserData(
            id = data.id!!,
            username = data.username!!,
            nickname = data.email!!,
            email = data.address!!,
            userPic = data.mobile,
            gender = null.toString(),
            birthDate = null.toString(),
            address = data.address!!,
            phone = data.mobile!!,
            wallet = data.wallet!!
        )
        return user
    }



    suspend fun updatePwd(data: UpdatePwdData): Response<Result<Objects>> {
        return withContext(Dispatchers.IO) {
            _serviceUser.updatePwd(data)
        }
    }
    suspend fun updateUser(data: User): Response<Result<Objects>> {
        var user = adapterUserData(data)
        var code = 1
        return withContext(Dispatchers.IO) {
            val response = _serviceUser.update(user)
            if (response.isSuccessful) {
                code = response.code()
                Log.d("update", code.toString())

            }
            return@withContext response
        }

    }

    suspend fun uploadAvatar(url: String): Response<Result<Objects>> {
        var code = 1
        return withContext(Dispatchers.IO) {
            val response = _serviceUser.uploadAvatar(url)
            if (response.isSuccessful) {
                code = response.code()
            }
            return@withContext response
        }

    }
    suspend fun redeemCode(redeemCode: String): Response<Result<Double>> {
        return withContext(Dispatchers.IO) {
            _serviceUser.redeemCode(redeemCode)
        }

    }


}
