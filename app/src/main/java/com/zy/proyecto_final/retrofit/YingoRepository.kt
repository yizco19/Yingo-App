package com.zy.proyecto_final.retrofit

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.zy.proyecto_final.pojo.Car
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.pojo.User
import com.zy.proyecto_final.retrofit.entities.CartItemData
import com.zy.proyecto_final.retrofit.entities.CategoryData
import com.zy.proyecto_final.retrofit.entities.PaymentData
import com.zy.proyecto_final.retrofit.entities.ProductData
import com.zy.proyecto_final.retrofit.entities.Result
import com.zy.proyecto_final.retrofit.entities.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.Objects

class YingoRepository(c: Context) {

    private lateinit var _context: Context
    private var _service: YingoAPI

    init {
        _context = c
        _service = YingoService.getApiService(_context)

    }
    suspend fun register(registerData: RegistrationData): Response<Result<Objects>> {
        return withContext(Dispatchers.IO){
            _service.register(registerData)
        }

    }
    suspend fun login(loginData: LoginData?): Response<Result<String>> {
        return withContext(Dispatchers.IO) {
            _service.login(loginData?.usernameOrEmail ?: "", loginData?.password ?: "")
        }
    }
    suspend fun getCategories(): List<Category> {

        return withContext(Dispatchers.IO) {
            var categories = mutableListOf<Category>()
            val response = _service.getCategories()
            if (response.isSuccessful) {
                response.body()?.data?.forEach {
                    categories.add(adapterCategory(it))
                    Log.i( "Categories", it.toString())
                }
            }
            return@withContext categories
        }
    }
    suspend fun getProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            var products = mutableListOf<Product>()
            val response = _service.getProducts()
            if (response.isSuccessful){
                response.body()?.data?.forEach {
                    if(it.visible)
                        products.add(adapterProduct(it))
                    Log.i( "products", it.toString())
                }

            }
            return@withContext products

        }
    }

    suspend fun processPayment(paymentData: PaymentData): Response<Result<Objects>> {
        return withContext(Dispatchers.IO) {
            _service.processPayment(paymentData)
        }
    }
    private fun adapterCategory(data: CategoryData): Category{
        var category = Category()
        category.name = data.name
        category.alias = data.alias
        category.id = data.id
        return category
    }
    private fun adapterProduct(data: ProductData): Product{
        var product = Product()
        product.id = data.id
        product.name = data.name
        product.description = data.description
        product.categoryId = data.categoryId
        product.price = data.price
        product.productPic = data.productPic
        return product


    }

    suspend fun getUser(): User? {
        return withContext(Dispatchers.IO) {
            var user = User()
            val response = _service.getUser()
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
        user.email = data.email
        user.id = data.id
        user.address = data.address
        user.mobile = data.phone
        user.userPic = data.userPic



        return user
    }


    suspend fun setCar(carLiveData: MutableList<Car>):  Response<Result<Objects>>  {
        var carItems = adapterCar(carLiveData.toMutableList())
        return withContext(Dispatchers.IO){
            _service.setCar(carItems)

        }
        }
    private fun adapterCar( data: MutableList<Car>):  MutableList<CartItemData>{
        var carItems = mutableListOf<CartItemData>()
        data.forEach {
            carItems.add(CartItemData(it.user_id!!,it.product_id!!,it.product_count!!))
        }
        return carItems
    }


}
