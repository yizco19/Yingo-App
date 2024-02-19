package com.zy.proyecto_final.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zy.proyecto_final.R
import com.zy.proyecto_final.fragment.CarFragment
import com.zy.proyecto_final.fragment.FavoritesFragment
import com.zy.proyecto_final.fragment.HomeFragment
import com.zy.proyecto_final.fragment.MineFragment
import com.zy.proyecto_final.fragment.OrderFragment

class MainActivity : AppCompatActivity() {
    lateinit var mBottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa la barra de navegación
        mBottomNav = findViewById(R.id.bottom_navigation)

        mBottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.order -> {
                    supportFragmentManager.beginTransaction().replace(R.id.content, OrderFragment()).commit()
                    true
                }
                R.id.car -> {
                    supportFragmentManager.beginTransaction().replace(R.id.content, CarFragment()).commit()
                    true
                }
                R.id.home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.content, HomeFragment()).commit()
                    true
                }
                R.id.favorites -> {
                    supportFragmentManager.beginTransaction().replace(R.id.content, FavoritesFragment()).commit()
                    true
                }
                R.id.mine -> {
                    supportFragmentManager.beginTransaction().replace(R.id.content, MineFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }
}
