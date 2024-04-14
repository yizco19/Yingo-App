package com.zy.proyecto_final.retrofit

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlinx.coroutines.runBlocking
import kotlin.collections.emptyList as emptyList1

class YingoViewModel : ViewModel() {

    private lateinit var _context: Context
    private lateinit var repository: YingoRepository
    private val resultLiveData = MutableLiveData<Int>()
    private lateinit var _categories : MutableList<Category>
    private lateinit var _products : MutableList<Product>

    fun init(c: Context) {
        this._context = c
        repository = YingoRepository(_context)
        _categories =  mutableListOf()
        _products =  mutableListOf()

    }


     fun register(registerData: RegistrationData): Boolean{
         var code = 1 // Valor predeterminado si ocurre un error
         runBlocking {
            val result = repository.register(registerData)
            val resultData = result.body()
             code = resultData?.code ?: 1
        }
         return code ==0

    }

    fun login(loginData: LoginData?): Boolean {
        var code = 1 // Valor predeterminado si ocurre un error

        runBlocking {
            val result = repository.login(loginData)
            val resultData = result.body()
            code = resultData?.code ?: 1
            val token = resultData?.data ?: ""
            storeTokenInJsonFile(token)
        }

        return code ==0
    }


    fun getCategories(): LiveData<List<Category>> {
        val categoriesLiveData = MutableLiveData<List<Category>>()
        viewModelScope.launch {
            val categories = repository.getCategories()
            _categories.addAll(categories)
            categoriesLiveData.postValue(_categories)
        }
        return categoriesLiveData
    }



    fun getProducts(): LiveData<List<Product>> {
        val productsLiveData = MutableLiveData<List<Product>>()
        viewModelScope.launch {
                val products = repository.getProducts()
                _products.addAll(products)
                productsLiveData.postValue(_products)

        }
        return productsLiveData
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
}