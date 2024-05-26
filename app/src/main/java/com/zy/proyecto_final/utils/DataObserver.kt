package com.zy.proyecto_final.utils

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.Offer
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.retrofit.YingoViewModel
import com.zy.proyecto_final.viewmodel.CategoryViewModel
import com.zy.proyecto_final.viewmodel.OfferViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel
import kotlinx.coroutines.runBlocking

class DataObserver(
    private val lifecycleOwner: LifecycleOwner,
    private val yingoModel: YingoViewModel,
) {

    fun observeCategories(categoryViewModel: CategoryViewModel, onComplete: () -> Unit) {
        runBlocking {
            val categoriesLiveData = yingoModel.getCategories()
            categoriesLiveData.observe(lifecycleOwner, Observer<List<Category>> { categories ->
                categories?.let {
                    categoryViewModel.setAll(categories)
                    onComplete()
                }
            })
        }
    }

    fun observeProducts(productViewModel: ProductViewModel) {
        runBlocking {
            val productsLiveData = yingoModel.getProducts()
            productsLiveData.observe(lifecycleOwner, Observer<List<Product>> { products ->
                products?.let {
                    productViewModel.setAll(products)
                }
            })
        }
    }

    fun observeUserData(userViewModel: UserViewModel) {
        yingoModel.getUserData().observe(lifecycleOwner, Observer { user ->
            user?.let {
                Log.d("getUser", user.toString())
                userViewModel.addUser(user)
            }
        })
    }

    fun observeOffers(offerViewModel: OfferViewModel) {
        runBlocking {
            val offersLiveData = yingoModel.getOffers()
            offersLiveData.observe(lifecycleOwner, Observer<List<Offer>> { offers ->
                offers?.let {
                    offerViewModel.setAll(offers)
                }
            })
        }
    }
}
