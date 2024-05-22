package com.zy.proyecto_final.util

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
class DataObserver(
    private val lifecycleOwner: LifecycleOwner,
    private val yingoModel: YingoViewModel,
) {

    fun observeCategories(categoryViewModel: CategoryViewModel, onComplete: () -> Unit) {
        val categoriesLiveData = yingoModel.getCategories()
        categoriesLiveData.observe(lifecycleOwner, Observer<List<Category>> { categories ->
            categories?.let {
                categoryViewModel.setAll(categories)
                onComplete()
            }
        })
    }

    fun observeProducts(productViewModel: ProductViewModel) {
        val productsLiveData = yingoModel.getProducts()
        productsLiveData.observe(lifecycleOwner, Observer<List<Product>> { products ->
            products?.let {
                productViewModel.setAll(products)
            }
        })
    }

    fun observeUserData(userViewModel: UserViewModel) {
        yingoModel.getUserData().observe(lifecycleOwner, Observer { user ->
            user?.let {
                userViewModel.addUser(user)
            }
        })
    }

    fun observeOffers(offerViewModel: OfferViewModel) {
        val offersLiveData = yingoModel.getOffers()
        offersLiveData.observe(lifecycleOwner, Observer<List<Offer>> { offers ->
            offers?.let {
                offerViewModel.setAll(offers)
            }
        })
    }
}
