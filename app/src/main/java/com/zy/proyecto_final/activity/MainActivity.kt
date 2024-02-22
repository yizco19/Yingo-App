package com.zy.proyecto_final.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
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
    lateinit var mNavView: NavigationView
    private val categoryviewmodel: CategoryViewModel by viewModels()
    private val productviewmodel: ProductViewModel by viewModels()
    private val carviewmodel: CarViewModel by viewModels()
    private val orderviewmodel: OrderViewModel by viewModels()
    private val userviewmodel: UserViewModel by viewModels()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.categoryviewmodel.init(this)
        this.productviewmodel.init(this)
        this.carviewmodel.init(this)
        this.orderviewmodel.init(this)
        this.userviewmodel.init(this)
        //asigna userlogged
        val user_id=intent.getLongExtra("userid",0)
        this.userviewmodel.userlogged= this.userviewmodel.getUserById(user_id)!!
        // Inicializa la barra de navegación
        mBottomNav = findViewById(R.id.bottom_navigation)
        mBottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.category -> {
                    findViewById<DrawerLayout>(R.id.drawer_layout).openDrawer(GravityCompat.START)
                    //llamada a CategoryFragment para que actualiza los datos de la barra de navegación
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
