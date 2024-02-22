package com.zy.proyecto_final.fragment

import android.os.Bundle
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
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            GridLayoutManager(context, 1)
        recyclerView.adapter =
            this.viewmodel.items.value?.let {
                OrderRecyclerViewAdapter(
                    it.toMutableList(), productviewmodel
                )
            }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    companion object {


    }

}