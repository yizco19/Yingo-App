package com.zy.proyecto_final.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zy.proyecto_final.R
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.recyclerviewadapter.OrderRecyclerViewAdapter

class OrderFragment : Fragment() {

    // Lista de productos simulada (debes obtenerla de tu fuente de datos)
    private val productList = mutableListOf(
        Product("Producto 1", "Producto 1", 1),
        Product("Producto 2", "Producto 2", 1),
        // Agrega más productos según sea necesario
    )

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnPlaceOrder: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order, container, false)

        return view
    }
}
