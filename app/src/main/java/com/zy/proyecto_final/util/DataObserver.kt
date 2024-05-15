package com.zy.proyecto_final.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.retrofit.YingoViewModel
import com.zy.proyecto_final.viewmodel.CategoryViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel

class DataObserver(private val lifecycleOwner: LifecycleOwner,
                   private val yingoModel: YingoViewModel,
                   private val categoryViewModel: CategoryViewModel,
                   private val productViewModel: ProductViewModel,
                   private val userViewModel: UserViewModel) {

    fun observeData() {
        val categoriesLiveData = yingoModel.getCategories()
        val productsLiveData = yingoModel.getProducts()

        categoriesLiveData.observe(lifecycleOwner, Observer<List<Category>> { categories ->
            categories?.let {
                categoryViewModel.setAll(categories)
            }
        })

        productsLiveData.observe(lifecycleOwner, Observer<List<Product>> { products ->
            products?.let {
                productViewModel.setAll(products)
            }
        })

        yingoModel.getUserData().observe(lifecycleOwner, Observer { user ->
            user?.let {
                userViewModel.addUser(user)
            }
        })
    }
}
