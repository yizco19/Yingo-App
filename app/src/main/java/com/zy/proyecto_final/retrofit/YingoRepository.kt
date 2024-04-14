package com.zy.proyecto_final.retrofit

import android.content.Context
import android.util.Log
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.retrofit.entities.CategoryData
import com.zy.proyecto_final.retrofit.entities.ProductData
import com.zy.proyecto_final.retrofit.entities.Result
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


}
