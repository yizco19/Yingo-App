package com.zy.proyecto_final.activity

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.zy.proyecto_final.R
import com.zy.proyecto_final.fragment.FavoritesFragment
import com.zy.proyecto_final.fragment.HomeFragment
import com.zy.proyecto_final.fragment.MineFragment
import com.zy.proyecto_final.fragment.CarFragment
import com.zy.proyecto_final.retrofit.YingoViewModel
import com.zy.proyecto_final.util.DataObserver
import com.zy.proyecto_final.viewmodel.CarViewModel
import com.zy.proyecto_final.viewmodel.CategoryViewModel
import com.zy.proyecto_final.viewmodel.FavoriteViewModel
import com.zy.proyecto_final.viewmodel.OfferViewModel
import com.zy.proyecto_final.viewmodel.OrderViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    lateinit var mBottomNav: BottomNavigationView
    lateinit var mNavView: NavigationView
    private val carviewmodel: CarViewModel by viewModels()
    private val orderviewmodel: OrderViewModel by viewModels()
    private val favoriteviewmodel: FavoriteViewModel by viewModels()
    private lateinit var settings: SharedPreferences
    private var issettings: Boolean = false
    private val categoryviewmodel: CategoryViewModel by viewModels()
    private val productviewmodel: ProductViewModel by viewModels()
    private val userviewmodel: UserViewModel by viewModels()
    private val yingomodel: YingoViewModel by viewModels()
    private val offermodel: OfferViewModel by viewModels()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.categoryviewmodel.init(this)
        this.productviewmodel.init(this)
        this.carviewmodel.init(this)
        this.orderviewmodel.init(this)
        this.userviewmodel.init(this)
        this.favoriteviewmodel.init(this)
        this.yingomodel.init(this)
        this.offermodel.init(this)
        //clear producto y category
        //productviewmodel.deleteAll()
        //categoryviewmodel.deleteAll()
        val dataObserver = DataObserver(this, yingomodel)

        dataObserver.observeOffers(offermodel)
        dataObserver.observeCategories(categoryviewmodel) {
            // Solo observar productos después de que las categorías se hayan cargado
            dataObserver.observeProducts(productviewmodel)
        }
        dataObserver.observeUserData(userviewmodel)


        //asigna userlogged
        settings = getSharedPreferences("user", MODE_PRIVATE)
        //val user_id=intent.getIntExtra("userid",0)
        //this.userviewmodel.userlogged= this.userviewmodel.getUserById(user_id)!!
        // Inicializa la barra de navegación
        mBottomNav = findViewById(R.id.bottom_navigation)
        issettings= settings.getBoolean("issettings", false)
        //loadData(issettings)
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

    /*private fun loadData(issettings: Boolean) {
        if (!issettings) {
            // Crea una lista de categorías
            val additionalCategories = List(20) { index ->
                com.zy.proyecto_final.pojo.Category(
                    name = when (index + 1) { // Ajusta aquí para comenzar desde 1
                        1 -> "Laptop"
                        2 -> "Tablet"
                        3 -> "Accesorios"
                        4 -> "Impresoras"
                        5 -> "Software"
                        6 -> "Almacenamiento"
                        7 -> "Redes"
                        8 -> "Audio"
                        9 -> "Monitores"
                        10 -> "Periféricos"
                        11 -> "Gaming"
                        12 -> "Componentes"
                        13 -> "Smartphones"
                        14 -> "Cámaras"
                        15 -> "Electrónica"
                        16 -> "Realidad Virtual"
                        17 -> "Oficina"
                        18 -> "Seguridad"
                        19 -> "Energía"
                        20 -> "Teclados"
                        else -> "Otra Categoría ${index + 1}"
                    }
                )
            }
            categoryviewmodel.insertAll(additionalCategories)


            val productData = listOf(
                Product("Laptop", "Laptop", 321.99, R.drawable.laptop, 1),
                Product("Tablet", "Tablet", 123.99, R.drawable.tablet, 2),
                Product("Accesorios", "Accesorios", 888.99, R.drawable.accesorios, 3),
                Product("Impresoras", "Impresoras", 666.99, R.drawable.impresora, 4),
                Product("Software", "Software", 444.99, R.drawable.software, 5),
                Product("Almacenamiento", "Almacenamiento", 555.99, R.drawable.almacenamiento, 6),
                Product("Redes", "Redes", 333.99, R.drawable.red, 7),
                Product("Audio", "Audio", 222.99, R.drawable.audio, 8),
                Product("Monitores", "Monitores", 666.99, R.drawable.monitor, 9),
                Product("Periféricos", "Periféricos", 777.99, R.drawable.perifericos, 10),
                Product("Gaming", "Gaming", 800.99, R.drawable.gaming, 11),
                Product("Componentes", "Componentes", 600.99, R.drawable.componentes, 12),
                Product("Smartphones", "Smartphones", 400.99, R.drawable.smartphones, 13),
                Product("Cámaras", "Cámaras", 250.99, R.drawable.camaras, 14),
                Product("Electrónica", "Electrónica", 200.99, R.drawable.electronica, 15),
                Product("Realidad Virtual", "Realidad Virtual", 1000.99, R.drawable.realidad_virtual, 16),
                Product("Oficina", "Oficina", 100.99, R.drawable.oficina, 17),
                Product("Seguridad", "Seguridad", 400.99, R.drawable.eguridad, 18),
                Product("Energía", "Energía", 300.00, R.drawable.energia, 19),
                Product("Teclados", "Teclados", 500.00, R.drawable.teclados, 20)
            )

            val productList = mutableListOf<Product>()

            productData.forEach { (name, description, price, imageUrl, categoryId) ->
                val product = Product(
                    name = name,
                    description = description,
                    categoryid = categoryId,
                    price = price,
                    imageUrl = imageUrl
                )
                productList.add(product)
            }

// Ahora, la lista productList contiene los productos que se pueden agregar a tu base de datos.
            productviewmodel.insertAll(productData)
        }
        with(settings.edit()) {
            putBoolean("issettings", true)
            apply()
            }
        }*/




}
