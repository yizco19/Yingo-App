package com.zy.proyecto_final.retrofit

import android.content.Context
import android.util.Log
import com.zy.proyecto_final.pojo.Car
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.Offer
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.retrofit.entities.CartItemData
import com.zy.proyecto_final.retrofit.entities.CategoryData
import com.zy.proyecto_final.retrofit.entities.OfferData
import com.zy.proyecto_final.retrofit.entities.OrderData
import com.zy.proyecto_final.retrofit.entities.OrderItem
import com.zy.proyecto_final.retrofit.entities.PaymentData
import com.zy.proyecto_final.retrofit.entities.ProductData
import com.zy.proyecto_final.retrofit.entities.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.Response
import java.util.Objects

class YingoRepository(c: Context) {

    private lateinit var _context: Context
    private var _service: YingoAPI
    private var _serviceUser: YingoUserAPI

    init {
        _context = c
        _service = YingoService.getApiService(_context)
        _serviceUser = YingoUserService.getApiService(_context)

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
    suspend fun getOrders (status : Int): List<OrderData>{
        return withContext(Dispatchers.IO) {
            var orders = mutableListOf<OrderData>()
            val response = _service.getOrders(status)
            if (response.isSuccessful) {
                response.body()?.data?.forEach {
                    orders.add(it)
                }
            }
            return@withContext orders
        }
    }
    suspend fun getOrderItems(id: Int): List<OrderItem> {
        return withContext(Dispatchers.IO) {
            var orderItems = mutableListOf<OrderItem>()
            val response = _service.getOrderItems(id)
            if (response.isSuccessful) {
                response.body()?.data?.forEach {
                    orderItems.add(it)
                }
            }
            return@withContext orderItems
        }

    }

    suspend fun getOffers(): List<Offer> {
        return withContext(Dispatchers.IO) {
            var offers = mutableListOf<Offer>()
            val response = _service.getOffers()
            if (response.isSuccessful) {
                response.body()?.data?.forEach {
                    offers.add(adapterOffer(it))
                }
            }
            return@withContext offers
        }
    }
    private fun adapterOffer(data: OfferData): Offer{
        var offer = Offer()
        offer.id = data.id
        offer.title = data.title
        offer.description = data.description
        offer.discount = data.discount
        return offer
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
        product.stock =data.stock
        //en caso de que exista una oferta
        if(data.offerId != 0 && data.offerId != null) {
            product.offerId = data.offerId
        }
        return product


    }


    suspend fun getOrderDetail(orderId: Int): OrderData {
        return withContext(Dispatchers.IO)
        {
            var order = OrderData()
            val response = _service.getOrderDetail(orderId)
            Log.i("responseOrder", response.toString())
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    order = it
                }
            }
            return@withContext order
        }

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

    suspend fun uploadImage(file: MultipartBody.Part): Response<Result<String>> {
        return withContext(Dispatchers.IO) {
            _service.uploadImage(file)
        }
    }




}
