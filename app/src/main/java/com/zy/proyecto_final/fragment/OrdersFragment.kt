package com.zy.proyecto_final.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.zy.proyecto_final.R
import com.zy.proyecto_final.recyclerviewadapter.OrdersRecyclerViewAdapter
import com.zy.proyecto_final.retrofit.YingoViewModel

class OrdersFragment : Fragment() {
    private val yingomodel: YingoViewModel by activityViewModels()
    private var view: View? = null
    private var ordersAdapter: OrdersRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val orderStatus = arguments?.getString("orderStatus")
        view = inflater.inflate(R.layout.fragment_orders, container, false)
        view?.findViewById<Toolbar>(R.id.toolbar)?.title = orderStatus
        ordersAdapter = context?.let { OrdersRecyclerViewAdapter(mutableListOf(),it,
            orderStatus.toString()
        ) }
        yingomodel.getOrders()
        view?.findViewById<RecyclerView>(R.id.listado)?.adapter = ordersAdapter

        observeOrders()
        return view
    }
    companion object {
        fun newInstance(orderStatus: String): OrdersFragment {
            val fragment = OrdersFragment()
            val args = Bundle()
            args.putString("orderStatus", orderStatus)
            fragment.arguments = args
            return fragment
        }
    }
}