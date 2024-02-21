package com.zy.proyecto_final.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zy.proyecto_final.R
import com.zy.proyecto_final.fragment.FavoritesFragment
import com.zy.proyecto_final.fragment.HomeFragment
import com.zy.proyecto_final.fragment.MineFragment
import com.zy.proyecto_final.fragment.CarFragment
import com.zy.proyecto_final.fragment.CategoryFragment
import com.zy.proyecto_final.viewmodel.CarViewModel
import com.zy.proyecto_final.viewmodel.CategoryViewModel
import com.zy.proyecto_final.viewmodel.OrderViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    lateinit var mBottomNav: BottomNavigationView
    private val categoryviewmodel: CategoryViewModel by viewModels()
    private val productviewmodel: ProductViewModel by viewModels()
    private val carviewmodel: CarViewModel by viewModels()
    private val orderviewmodel: OrderViewModel by viewModels()
    private val userviewmodel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.categoryviewmodel.init(this)
        this.productviewmodel.init(this)
        this.carviewmodel.init(this)
        this.orderviewmodel.init(this)
        this.userviewmodel.init(this)
        // Inicializa la barra de navegación
        mBottomNav = findViewById(R.id.bottom_navigation)

        mBottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.category -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, CategoryFragment()).commit()
                    true
                }
                R.id.car -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, CarFragment()).commit()
                    true
                }
                R.id.home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, HomeFragment()).commit()
                    true
                }
                R.id.favorites -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, FavoritesFragment()).commit()
                    true
                }
                R.id.mine -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, MineFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }
}
