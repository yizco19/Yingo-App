package com.zy.proyecto_final.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zy.proyecto_final.R
import com.zy.proyecto_final.recyclerviewadapter.CarRecyclerViewAdapter
import com.zy.proyecto_final.recyclerviewadapter.OrderRecyclerViewAdapter
import com.zy.proyecto_final.viewmodel.OrderViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel
import java.math.BigDecimal

class OrderFragment : Fragment() {
    private val viewmodel: OrderViewModel by activityViewModels<OrderViewModel>()
    private val productviewmodel: ProductViewModel by activityViewModels<ProductViewModel>()
    private lateinit var txt_total : TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnBuy: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order, container, false)
        recyclerView = view.findViewById(R.id.recyclerView_order)
        recyclerView.layoutManager =
            GridLayoutManager(context, 1)
        recyclerView.adapter =
            this.viewmodel.items.value?.let {
                OrderRecyclerViewAdapter(
                    it.toMutableList(), productviewmodel
                )
            }

        // Inflate the layout for this fragment
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData() // Move the loadData() call to onViewCreated
        var precio = BigDecimal.ZERO

        viewmodel.items.value?.let { items ->
            for (i in items) {
                i.product_id?.let { productId ->
                    val product = productviewmodel.getProductbyId(productId)
                    val productPrice = product?.price?.toBigDecimal() ?: BigDecimal.ZERO
                    precio += productPrice * (i.product_count?.toBigDecimal() ?: BigDecimal.ZERO)
                }
            }
        }
        view?.findViewById<TextView>(R.id.total)?.text = precio.toString()
    }
    private fun loadData() {
        viewmodel.items.value?.let {
            (view?.findViewById<RecyclerView>(R.id.recyclerView_order)!!.adapter as OrderRecyclerViewAdapter).setValues(
                it.toMutableList()
            )
        }
    }


}