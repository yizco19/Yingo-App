package com.zy.proyecto_final.retrofit

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zy.proyecto_final.pojo.Car
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.Offer
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.pojo.User
import com.zy.proyecto_final.retrofit.entities.OrderData
import com.zy.proyecto_final.retrofit.entities.OrderItem
import com.zy.proyecto_final.retrofit.entities.PaymentData
import com.zy.proyecto_final.retrofit.entities.Result
import com.zy.proyecto_final.retrofit.entities.UpdatePwdData
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.util.Objects

class YingoViewModel : ViewModel() {

    private lateinit var _selectedOrder: OrderData
    private lateinit var _context: Context
    private lateinit var repository: YingoRepository
    private lateinit var repositoryUser: YingoUserRepository
    private val resultLiveData = MutableLiveData<Int>()
    private lateinit var _categories: MutableList<Category>
    private lateinit var _products: MutableList<Product>
    private lateinit var _offers: MutableList<Offer>
    private lateinit var _orderItems: MutableList<OrderItem>
    private lateinit var _orders: MutableLiveData<MutableList<OrderData>>


    public val orders: LiveData<MutableList<OrderData>>
        get() = _orders

    fun init(c: Context) {
        this._context = c
        repository = YingoRepository(_context)
        repositoryUser = YingoUserRepository(_context)
        _categories = mutableListOf()
        _products = mutableListOf()
        _offers = mutableListOf()
        _orders = MutableLiveData<MutableList<OrderData>>()
        _orderItems = mutableListOf()
        _selectedOrder = OrderData()

    }


    fun register(registerData: RegistrationData): Boolean {
        var code = 1 // Valor predeterminado si ocurre un error
        runBlocking {
            val result = repositoryUser.register(registerData)
            val resultData = result.body()
            code = resultData?.code ?: 1
        }
        return code == 0

    }

    fun login(loginData: LoginData?): Boolean {
        var code = 1 // Valor predeterminado si ocurre un error

        runBlocking {
            try {
                val result = repositoryUser.login(loginData)
                val resultData = result.body()
                code = resultData?.code ?: 1
                val token = resultData?.data ?: ""
                storeTokenInJsonFile(token)
            } catch (e: Exception) {
                Log.e("YingoViewModel", "Error during login", e)
            }
        }

        return code == 0 // Devuelve true si el código es 0 (inicio de sesión exitoso)
    }



    fun getCategories(): LiveData<List<Category>> {
        val categoriesLiveData = MutableLiveData<List<Category>>()
        runBlocking{
            val categories = repository.getCategories()
            _categories.addAll(categories)
            categoriesLiveData.postValue(_categories)
        }
        return categoriesLiveData
    }

    fun getProducts(): LiveData<List<Product>> {
        val productsLiveData = MutableLiveData<List<Product>>()
        runBlocking{
            val products = repository.getProducts()
            _products.addAll(products)
            productsLiveData.postValue(_products)

        }
        return productsLiveData
    }
    fun getOffers(): LiveData<List<Offer>> {
        val offersLiveData = MutableLiveData<List<Offer>>()
        runBlocking{
            val offers = repository.getOffers()
            _offers.addAll(offers)
            offersLiveData.postValue(_offers)
        }
        return offersLiveData
    }
    fun getOrderItems(id: Int): LiveData<List<OrderItem>> {
        val orderItemsLiveData = MutableLiveData<List<OrderItem>>()
        viewModelScope.launch {
            val orderItems = repository.getOrderItems(id)
            _orderItems.addAll(orderItems)
            orderItemsLiveData.postValue(_orderItems)
        }
        return orderItemsLiveData

    }

    fun getOrders(status: Int) {
        viewModelScope.launch {
            _orders.value = repository.getOrders(status).toMutableList()
        }
    }

    private fun storeTokenInJsonFile(token: String) {
        val tokenFile = File(_context.filesDir, "token.json")
        try {
            FileOutputStream(tokenFile).use { stream ->
                stream.write(token.toByteArray())
            }
            Log.d("YingoViewModel", "Token stored in JSON file successfully.")
        } catch (e: IOException) {
            Log.e("YingoViewModel", "Error storing token in JSON file: ${e.message}")
        }
    }

    fun getUserData(): MutableLiveData<User?> {
        val userLiveData = MutableLiveData<User?>()
        runBlocking {
            val user = repositoryUser.getUser()
            userLiveData.postValue(user)

        }
        return userLiveData

    }
    fun getOrderDetail(position: Int): LiveData<OrderData?> {
        val orderLiveData = MutableLiveData<OrderData?>()
        runBlocking {
            val order = repository.getOrderDetail(position)
            Log.d("YingoViewModel", "order: $order")
            orderLiveData.postValue(order)
        }
        return orderLiveData
    }

    fun setCar(value: MutableList<Car>): Int {
        val carLiveData = MutableLiveData(value)
        var code = 1 // Valor predeterminado si ocurre un error
        runBlocking {
            val result = repository.setCar(carLiveData.value!!)
            val resultData = result.body()
            code = resultData?.code ?: 1
        }
        return code

    }

    fun processPayment(paymentData: PaymentData): Result<Objects>? {
        var code = 1 // Valor predeterminado si ocurre un error
        var resultData: Result<Objects>? = null
        runBlocking {
            val result = repository.processPayment(paymentData)
            resultData = result.body()
            code = resultData?.code ?: 1
            Log.d("YingoViewModel", "Respuesta del servidor: ${resultData!!.message}")

        }
        return resultData


    }




    fun updatePwd(data: UpdatePwdData): Result<Objects>? {
        var code = 1 // Valor predeterminado si ocurre un error
        var resultData: Result<Objects>? = null
        runBlocking {
            val result = repositoryUser.updatePwd(data)
            Log.d("YingoViewModelPwd", "Respuesta del servidor: ${result!!}")
            resultData = result.body()
            Log.d("YingoViewModelPwd", "Respuesta del servidor: ${resultData?.code}")
            code = resultData?.code ?: 1
        }
        return resultData
    }

    fun updateUser(data: User): Int {
        var code = 1 // Valor predeterminado si ocurre un error
        runBlocking {
            val result = repositoryUser.updateUser(data)
            val resultData = result.body()
            code = resultData?.code ?: 1
        }
        return code
    }

    fun uploadAvatar(uri: Uri): Int {
        var code = 1 // Valor predeterminado si ocurre un error

        runBlocking {
            try {
                val file = File(uri.path ?: throw IllegalArgumentException("URI path is null"))
                val requestFile: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                val body: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestFile)

                // Llama al método del repositorio para subir la imagen
                val result: Response<Result<String>> = repository.uploadImage(body)
                val resultData = result.body()
                if (resultData != null) {
                    Log.d("YingoViewModelUpload", "Respuesta del servidor: ${resultData}")
                    code = resultData.code ?: 1

                    if (resultData.code == 0) {
                        val avatarUrl = resultData.data ?: throw IllegalArgumentException("Avatar URL is null")
                        val updateResult = repositoryUser.uploadAvatar(avatarUrl)
                        val updateResultData = updateResult.body()
                        if (updateResultData != null) {
                            Log.d("YingoViewModelUpload", "Respuesta del servidor: ${updateResultData}")
                            code = updateResultData.code ?: 1
                        } else {
                            Log.e("YingoViewModelUpload", "Update result data is null")
                        }
                    } else {
                        Log.e("YingoViewModelUpload", "Result code is ${resultData.code}")
                    }
                } else {
                    Log.e("YingoViewModelUpload", "Result data is null")
                }
            } catch (e: Exception) {
                Log.e("YingoViewModelUpload", "Error uploading avatar", e)
            }
        }

        return code
    }
    fun redeemCode (redeemCode: String): Result<Double>? {
        var code = 1 // Valor predeterminado si ocurre un error
        var resultData: Result<Double>? = null
        runBlocking {
            val result = repositoryUser.redeemCode(redeemCode)
            resultData = result.body()
            code = resultData?.code ?: 1
        }
        return resultData
    }


}